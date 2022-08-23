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