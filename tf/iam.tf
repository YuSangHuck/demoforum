data "aws_iam_policy_document" "simple_lambda_assume_role_policy" {
  statement {
    actions = ["sts:AssumeRole"]

    principals {
      identifiers = ["lambda.amazonaws.com"]
      type        = "Service"
    }
    effect = "Allow"
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
