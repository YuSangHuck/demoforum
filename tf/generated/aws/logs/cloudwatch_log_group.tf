resource "aws_cloudwatch_log_group" "tfer---002F-aws-002F-http-api-002F-demoForum-dev" {
  name = "/aws/http-api/demoForum-dev"

  tags = {
    STAGE = "dev"
  }

  tags_all = {
    STAGE = "dev"
  }
}

resource "aws_cloudwatch_log_group" "tfer---002F-aws-002F-http-api-002F-tf-demo-forum" {
  name              = "/aws/http-api/tf-demo-forum"
  retention_in_days = "1"

  tags = {
    CREATED_BY = "tf"
    NAME       = "tf-demo-forum-log-apigatewayv2"
    PROJECT    = "demo-forum"
    STAGE      = "dev"
  }

  tags_all = {
    CREATED_BY = "tf"
    NAME       = "tf-demo-forum-log-apigatewayv2"
    PROJECT    = "demo-forum"
    STAGE      = "dev"
  }
}

resource "aws_cloudwatch_log_group" "tfer---002F-aws-002F-lambda-002F-demoForum-dev-demoForum" {
  name = "/aws/lambda/demoForum-dev-demoForum"

  tags = {
    STAGE = "dev"
  }

  tags_all = {
    STAGE = "dev"
  }
}

resource "aws_cloudwatch_log_group" "tfer---002F-aws-002F-lambda-002F-tf-demo-forum" {
  name              = "/aws/lambda/tf-demo-forum"
  retention_in_days = "1"

  tags = {
    CREATED_BY = "tf"
    NAME       = "tf-demo-forum-log-lambda"
    PROJECT    = "demo-forum"
    STAGE      = "dev"
  }

  tags_all = {
    CREATED_BY = "tf"
    NAME       = "tf-demo-forum-log-lambda"
    PROJECT    = "demo-forum"
    STAGE      = "dev"
  }
}
