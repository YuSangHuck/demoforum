resource "aws_iam_policy" "tfer--AWSLambdaAMIExecutionRole-233107d0-47d6-44d5-b0c3-accaee12e26f" {
  name = "AWSLambdaAMIExecutionRole-233107d0-47d6-44d5-b0c3-accaee12e26f"
  path = "/service-role/"

  policy = <<POLICY
{
  "Statement": [
    {
      "Action": [
        "ec2:DescribeImages"
      ],
      "Effect": "Allow",
      "Resource": "*"
    }
  ],
  "Version": "2012-10-17"
}
POLICY
}

resource "aws_iam_policy" "tfer--AWSLambdaBasicExecutionRole-862f3aee-8157-4e44-8ae4-21b18fcbe7dd" {
  name = "AWSLambdaBasicExecutionRole-862f3aee-8157-4e44-8ae4-21b18fcbe7dd"
  path = "/service-role/"

  policy = <<POLICY
{
  "Statement": [
    {
      "Action": "logs:CreateLogGroup",
      "Effect": "Allow",
      "Resource": "arn:aws:logs:ap-northeast-2:752417200383:*"
    },
    {
      "Action": [
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ],
      "Effect": "Allow",
      "Resource": [
        "arn:aws:logs:ap-northeast-2:752417200383:log-group:/aws/lambda/hello-lambda-golang:*"
      ]
    }
  ],
  "Version": "2012-10-17"
}
POLICY
}

resource "aws_iam_policy" "tfer--AWSLambdaBasicExecutionRole-8eadf6e3-9662-47d2-bffb-03b2ccc7645d" {
  name = "AWSLambdaBasicExecutionRole-8eadf6e3-9662-47d2-bffb-03b2ccc7645d"
  path = "/service-role/"

  policy = <<POLICY
{
  "Statement": [
    {
      "Action": "logs:CreateLogGroup",
      "Effect": "Allow",
      "Resource": "arn:aws:logs:ap-northeast-2:752417200383:*"
    },
    {
      "Action": [
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ],
      "Effect": "Allow",
      "Resource": [
        "arn:aws:logs:ap-northeast-2:752417200383:log-group:/aws/lambda/demoResizer:*"
      ]
    }
  ],
  "Version": "2012-10-17"
}
POLICY
}

resource "aws_iam_policy" "tfer--AWSLambdaBasicExecutionRole-952f0244-e997-4b87-b090-af517e5ca1af" {
  name = "AWSLambdaBasicExecutionRole-952f0244-e997-4b87-b090-af517e5ca1af"
  path = "/service-role/"

  policy = <<POLICY
{
  "Statement": [
    {
      "Action": "logs:CreateLogGroup",
      "Effect": "Allow",
      "Resource": "arn:aws:logs:ap-northeast-2:752417200383:*"
    },
    {
      "Action": [
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ],
      "Effect": "Allow",
      "Resource": [
        "arn:aws:logs:ap-northeast-2:752417200383:log-group:/aws/lambda/test:*"
      ]
    }
  ],
  "Version": "2012-10-17"
}
POLICY
}

resource "aws_iam_policy" "tfer--AWSLambdaBasicExecutionRole-ab994e3d-750d-4b74-96ef-54c43fe5c424" {
  name = "AWSLambdaBasicExecutionRole-ab994e3d-750d-4b74-96ef-54c43fe5c424"
  path = "/service-role/"

  policy = <<POLICY
{
  "Statement": [
    {
      "Action": "logs:CreateLogGroup",
      "Effect": "Allow",
      "Resource": "arn:aws:logs:ap-northeast-2:752417200383:*"
    },
    {
      "Action": [
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ],
      "Effect": "Allow",
      "Resource": [
        "arn:aws:logs:ap-northeast-2:752417200383:log-group:/aws/lambda/FunctionName-HellowWorldPython:*"
      ]
    }
  ],
  "Version": "2012-10-17"
}
POLICY
}

resource "aws_iam_policy" "tfer--AWSLambdaBasicExecutionRole-b834034d-afb0-4b2a-bd8b-fc86839a6544" {
  name = "AWSLambdaBasicExecutionRole-b834034d-afb0-4b2a-bd8b-fc86839a6544"
  path = "/service-role/"

  policy = <<POLICY
{
  "Statement": [
    {
      "Action": "logs:CreateLogGroup",
      "Effect": "Allow",
      "Resource": "arn:aws:logs:ap-northeast-2:752417200383:*"
    },
    {
      "Action": [
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ],
      "Effect": "Allow",
      "Resource": [
        "arn:aws:logs:ap-northeast-2:752417200383:log-group:/aws/lambda/my-demo:*"
      ]
    }
  ],
  "Version": "2012-10-17"
}
POLICY
}

resource "aws_iam_policy" "tfer--AWSLambdaBasicExecutionRole-c926a223-f0bb-4ab3-9794-a1f0f4470f75" {
  name = "AWSLambdaBasicExecutionRole-c926a223-f0bb-4ab3-9794-a1f0f4470f75"
  path = "/service-role/"

  policy = <<POLICY
{
  "Statement": [
    {
      "Action": "logs:CreateLogGroup",
      "Effect": "Allow",
      "Resource": "arn:aws:logs:ap-northeast-2:752417200383:*"
    },
    {
      "Action": [
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ],
      "Effect": "Allow",
      "Resource": [
        "arn:aws:logs:ap-northeast-2:752417200383:log-group:/aws/lambda/hello-lambda-nodejs:*"
      ]
    }
  ],
  "Version": "2012-10-17"
}
POLICY
}

resource "aws_iam_policy" "tfer--AWSLambdaBasicExecutionRole-d64a8c48-e35a-4847-b242-f3736ef477d2" {
  name = "AWSLambdaBasicExecutionRole-d64a8c48-e35a-4847-b242-f3736ef477d2"
  path = "/service-role/"

  policy = <<POLICY
{
  "Statement": [
    {
      "Action": "logs:CreateLogGroup",
      "Effect": "Allow",
      "Resource": "arn:aws:logs:ap-northeast-2:752417200383:*"
    },
    {
      "Action": [
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ],
      "Effect": "Allow",
      "Resource": [
        "arn:aws:logs:ap-northeast-2:752417200383:log-group:/aws/lambda/hello-lambda-csharp:*"
      ]
    }
  ],
  "Version": "2012-10-17"
}
POLICY
}

resource "aws_iam_policy" "tfer--AWSLambdaBasicExecutionRole-dc0a5d3a-41d6-4771-95ad-6e948cb2592f" {
  name = "AWSLambdaBasicExecutionRole-dc0a5d3a-41d6-4771-95ad-6e948cb2592f"
  path = "/service-role/"

  policy = <<POLICY
{
  "Statement": [
    {
      "Action": "logs:CreateLogGroup",
      "Effect": "Allow",
      "Resource": "arn:aws:logs:ap-northeast-2:752417200383:*"
    },
    {
      "Action": [
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ],
      "Effect": "Allow",
      "Resource": [
        "arn:aws:logs:ap-northeast-2:752417200383:log-group:/aws/lambda/s:*"
      ]
    }
  ],
  "Version": "2012-10-17"
}
POLICY
}

resource "aws_iam_policy" "tfer--AWSLambdaBasicExecutionRole-f4882310-8204-4908-80f3-0f57e787f33f" {
  name = "AWSLambdaBasicExecutionRole-f4882310-8204-4908-80f3-0f57e787f33f"
  path = "/service-role/"

  policy = <<POLICY
{
  "Statement": [
    {
      "Action": "logs:CreateLogGroup",
      "Effect": "Allow",
      "Resource": "arn:aws:logs:ap-northeast-2:752417200383:*"
    },
    {
      "Action": [
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ],
      "Effect": "Allow",
      "Resource": [
        "arn:aws:logs:ap-northeast-2:752417200383:log-group:/aws/lambda/s:*"
      ]
    }
  ],
  "Version": "2012-10-17"
}
POLICY
}

resource "aws_iam_policy" "tfer--AWSLambdaEdgeExecutionRole-1966e8cc-bf49-41c8-83ec-946b155503a7" {
  name = "AWSLambdaEdgeExecutionRole-1966e8cc-bf49-41c8-83ec-946b155503a7"
  path = "/service-role/"

  policy = <<POLICY
{
  "Statement": [
    {
      "Action": [
        "logs:CreateLogGroup",
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ],
      "Effect": "Allow",
      "Resource": [
        "arn:aws:logs:*:*:*"
      ]
    }
  ],
  "Version": "2012-10-17"
}
POLICY
}

resource "aws_iam_policy" "tfer--AWSLambdaEdgeExecutionRole-3d407cd8-7f92-4c5e-89cc-dee8525c3931" {
  name = "AWSLambdaEdgeExecutionRole-3d407cd8-7f92-4c5e-89cc-dee8525c3931"
  path = "/service-role/"

  policy = <<POLICY
{
  "Statement": [
    {
      "Action": [
        "logs:CreateLogGroup",
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ],
      "Effect": "Allow",
      "Resource": [
        "arn:aws:logs:*:*:*"
      ]
    }
  ],
  "Version": "2012-10-17"
}
POLICY
}

resource "aws_iam_policy" "tfer--AWSLambdaEdgeExecutionRole-5568d390-8e89-48ff-be90-66f40a387ec7" {
  name = "AWSLambdaEdgeExecutionRole-5568d390-8e89-48ff-be90-66f40a387ec7"
  path = "/service-role/"

  policy = <<POLICY
{
  "Statement": [
    {
      "Action": [
        "logs:CreateLogGroup",
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ],
      "Effect": "Allow",
      "Resource": [
        "arn:aws:logs:*:*:*"
      ]
    }
  ],
  "Version": "2012-10-17"
}
POLICY
}

resource "aws_iam_policy" "tfer--AWSLambdaEdgeExecutionRole-cf4b3851-f67a-4403-a4e0-2854e6830187" {
  name = "AWSLambdaEdgeExecutionRole-cf4b3851-f67a-4403-a4e0-2854e6830187"
  path = "/service-role/"

  policy = <<POLICY
{
  "Statement": [
    {
      "Action": [
        "logs:CreateLogGroup",
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ],
      "Effect": "Allow",
      "Resource": [
        "arn:aws:logs:*:*:*"
      ]
    }
  ],
  "Version": "2012-10-17"
}
POLICY
}

resource "aws_iam_policy" "tfer--AWSLambdaTracerAccessExecutionRole-e39cbd3d-752c-4c6e-87b1-d35f293a1bd3" {
  name = "AWSLambdaTracerAccessExecutionRole-e39cbd3d-752c-4c6e-87b1-d35f293a1bd3"
  path = "/service-role/"

  policy = <<POLICY
{
  "Statement": {
    "Action": [
      "xray:PutTraceSegments",
      "xray:PutTelemetryRecords"
    ],
    "Effect": "Allow",
    "Resource": [
      "*"
    ]
  },
  "Version": "2012-10-17"
}
POLICY
}

resource "aws_iam_policy" "tfer--demo_resizer_lambda_policy" {
  name = "demo_resizer_lambda_policy"
  path = "/"

  policy = <<POLICY
{
  "Statement": [
    {
      "Action": [
        "iam:CreateServiceLinkedRole",
        "lambda:GetFunction",
        "lambda:EnableReplication",
        "cloudfront:UpdateDistribution",
        "s3:GetObject",
        "logs:CreateLogGroup",
        "logs:CreateLogStream",
        "logs:PutLogEvents",
        "logs:DescribeLogStreams"
      ],
      "Effect": "Allow",
      "Resource": "*",
      "Sid": "VisualEditor0"
    }
  ],
  "Version": "2012-10-17"
}
POLICY
}
