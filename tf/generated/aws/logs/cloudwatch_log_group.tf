resource "aws_cloudwatch_log_group" "tfer---002F-aws-002F-http-api-002F-demoForum-dev" {
  name = "/aws/http-api/demoForum-dev"

  tags = {
    STAGE = "dev"
  }

  tags_all = {
    STAGE = "dev"
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
