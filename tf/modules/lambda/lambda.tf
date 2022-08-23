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
  filename         = var.filename
  role             = aws_iam_role.tf-lambda-iam.arn
  runtime          = "java11"
  source_code_hash = filebase64sha256(var.filename)

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
