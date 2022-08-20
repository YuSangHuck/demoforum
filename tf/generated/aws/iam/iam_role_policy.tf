resource "aws_iam_role_policy" "tfer--demoForum-dev-ap-northeast-2-lambdaRole_demoForum-dev-lambda" {
  name = "demoForum-dev-lambda"

  policy = <<POLICY
{
  "Statement": [
    {
      "Action": [
        "logs:CreateLogStream",
        "logs:CreateLogGroup"
      ],
      "Effect": "Allow",
      "Resource": [
        "arn:aws:logs:ap-northeast-2:752417200383:log-group:/aws/lambda/demoForum-dev*:*"
      ]
    },
    {
      "Action": [
        "logs:PutLogEvents"
      ],
      "Effect": "Allow",
      "Resource": [
        "arn:aws:logs:ap-northeast-2:752417200383:log-group:/aws/lambda/demoForum-dev*:*:*"
      ]
    },
    {
      "Action": [
        "xray:PutTraceSegments",
        "xray:PutTelemetryRecords"
      ],
      "Effect": "Allow",
      "Resource": [
        "*"
      ]
    }
  ],
  "Version": "2012-10-17"
}
POLICY

  role = "demoForum-dev-ap-northeast-2-lambdaRole"
}
