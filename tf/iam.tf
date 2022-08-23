data "aws_iam_policy_document" "simple_lambda_assume_role_policy" {
  #  lambda 실행
  statement {
    actions = ["sts:AssumeRole"]

    principals {
      identifiers = ["lambda.amazonaws.com"]
      type        = "Service"
    }
    effect = "Allow"
  }
  #   "logs:CreateLogStream","logs:CreateLogGroup"
  statement {
    actions   = ["logs:CreateLogStream", "logs:CreateLogGroup"]
    resources = "${aws_cloudwatch_log_group.tf-lambda.arn}:*"
    effect    = "Allow"
  }
  #  "logs:PutLogEvents"
  statement {
    actions   = ["logs:PutLogEvents"]
    resources = "${aws_cloudwatch_log_group.tf-lambda.arn}:*:*"
    effect    = "Allow"
  }
}

resource "aws_iam_role" "tf-lambda-iam" {
  name               = "${local.prefix}-role"
  assume_role_policy = data.aws_iam_policy_document.simple_lambda_assume_role_policy.json
  tags               = merge(
    {
      NAME = "${local.prefix}-role"
    },
    local.common_tags
  )
}
