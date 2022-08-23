locals {
  cw_log_group_name_lambda       = "/aws/lambda/${aws_lambda_function.tf-lambda.function_name}"
  cw_log_group_name_apigatewayv2 = "/aws/http-api/${aws_lambda_function.tf-lambda.function_name}"
  cw_log_retention_in_days       = 1
}

resource "aws_cloudwatch_log_group" "tf-lambda" {
  name              = local.cw_log_group_name_lambda
  retention_in_days = local.cw_log_retention_in_days
  tags              = merge(
    {
      NAME = "${local.prefix}-log-lambda"
    },
    local.common_tags
  )
}

resource "aws_cloudwatch_log_group" "tf-apigatewayv2" {
  name              = local.cw_log_group_name_apigatewayv2
  retention_in_days = local.cw_log_retention_in_days
  tags              = merge(
    {
      NAME = "${local.prefix}-log-apigatewayv2"
    },
    local.common_tags
  )
}