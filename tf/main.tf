data "archive_file" "tf-lambda-zip" {
  source_dir  = "../build/distributions"
  output_path = "demoForum-0.0.1-SNAPSHOT.zip"
  type = "zip"
}

resource "aws_iam_role" "tf-lambda-iam" {
  name               = "tf-lambda-iam"
  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": "sts:AssumeRole",
      "Principal": {
        "Service": "lambda.amazonaws.com"
      },
      "Effect": "Allow"
    }
  ]
}
EOF
  tags               = {
    CREATED_BY = "tf"
  }
}

resource "aws_lambda_function" "tf-lambda" {
  environment {
    variables = {
      SPRING_PROFILES_ACTIVE = "prod"
    }
  }

  function_name    = "tf-lambda-function"
  handler          = "com.demo.demoforum.LambdaHandler::handleRequest"
  memory_size      = 2048
  package_type     = "Zip"
  filename         = data.archive_file.tf-lambda-zip.output_path
  role             = aws_iam_role.tf-lambda-iam.arn
  runtime          = "java11"
  source_code_hash = data.archive_file.tf-lambda-zip.output_base64sha256

  tags = {
    CREATED_BY = "tf"
  }

  timeout = 60

  tracing_config {
    mode = "Active"
  }
}

resource "aws_lambda_permission" "tf-api-gw" {
  statement_id  = "AllowExecutionFromApiGateway"
  action        = "lambda:InvokeFunction"
  function_name = aws_lambda_function.tf-lambda.arn
  principal     = "apigateway.amazonaws.com"
  source_arn    = "${aws_apigatewayv2_api.tf-lambda-api.execution_arn}/*/*/*"
  #  /*/*/*가 의미가 뭐지?
}


resource "aws_apigatewayv2_api" "tf-lambda-api" {
  name          = "tf-v2-http-api"
  protocol_type = "HTTP"
  tags          = {
    CREATED_BY = "tf"
  }
}

resource "aws_apigatewayv2_stage" "tf-lambda-stage" {
  api_id      = aws_apigatewayv2_api.tf-lambda-api.id
  name        = "$default"
  auto_deploy = true
  tags        = {
    CREATED_BY = "tf"
  }
}

resource "aws_apigatewayv2_integration" "tf-lambda-integration" {
  api_id               = aws_apigatewayv2_api.tf-lambda-api.id
  integration_type     = "AWS_PROXY"
  integration_method   = "POST"
  integration_uri      = aws_lambda_function.tf-lambda.invoke_arn
  passthrough_behavior = "WHEN_NO_MATCH"
  payload_format_version = "1.0"
  timeout_milliseconds = 30000
}

resource "aws_apigatewayv2_route" "tf-lambda_route" {
  api_id    = aws_apigatewayv2_api.tf-lambda-api.id
  route_key = "$default"
  target    = "integrations/${aws_apigatewayv2_integration.tf-lambda-integration.id}"
}
