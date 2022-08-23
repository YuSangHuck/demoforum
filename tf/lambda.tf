locals {
  lambda_filename              = "../build/distributions/demoForum-0.0.1-SNAPSHOT.zip"
  lambda_handler               = "com.demo.demoforum.LambdaHandler::handleRequest"
  lambda_description           = "This is demoForum Lambda function"
  lambda_runtime               = "java11"
  lambda_timeout               = 60
  lambda_memory_size           = 2048
  lambda_package_type          = "Zip"
  lambda_concurrent_executions = -1
}

resource "aws_lambda_function" "tf-lambda" {
  environment {
    variables = {
      SPRING_PROFILES_ACTIVE = "prod"
    }
  }
  function_name    = "${local.prefix}-lambda"
  description      = "${local.lambda_description}"
  handler          = "${local.lambda_handler}"
  memory_size      = "${local.lambda_memory_size}"
  package_type     = "${local.lambda_package_type}"
  filename         = "${local.lambda_filename}"
  role             = aws_iam_role.tf-lambda-iam.arn
  runtime          = "${local.lambda_runtime}"
  source_code_hash = filebase64sha256("${local.lambda_filename}")

  tags = merge(
    {
      Name = "${local.prefix}-lambda"
    },
    local.common_tags
  )

  timeout = "${local.lambda_timeout}"

  reserved_concurrent_executions = local.lambda_concurrent_executions

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
