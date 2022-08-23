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
  access_log_settings {
    destination_arn = "${aws_cloudwatch_log_group.tf-apigatewayv2.arn}"
    format          = "{ \"requestId\": \"$context.requestId\", \"ip\": \"$context.identity.sourceIp\", \"requestTime\": \"$context.requestTime\", \"httpMethod\": \"$context.httpMethod\", \"routeKey\": \"$context.routeKey\", \"status\": \"$context.status\", \"protocol\": \"$context.protocol\", \"responseLength\": \"$context.responseLength\", \"path\": \"$context.path\", \"basePathMatched\": \"$context.customDomain.basePathMatched\" }"
  }
  depends_on = [aws_cloudwatch_log_group.tf-apigatewayv2]
}

resource "aws_apigatewayv2_integration" "tf-lambda-integration" {
  api_id                 = aws_apigatewayv2_api.tf-lambda-api.id
  integration_type       = "AWS_PROXY"
  integration_method     = "POST"
  integration_uri        = aws_lambda_function.tf-lambda.invoke_arn
  passthrough_behavior   = "WHEN_NO_MATCH"
  payload_format_version = "1.0"
  timeout_milliseconds   = 30000
}

resource "aws_apigatewayv2_route" "tf-lambda_route" {
  api_id    = aws_apigatewayv2_api.tf-lambda-api.id
  route_key = "$default"
  target    = "integrations/${aws_apigatewayv2_integration.tf-lambda-integration.id}"
}

