resource "aws_lambda_function" "tfer--demoForum-dev-demoForum" {
  environment {
    variables = {
      SPRING_PROFILES_ACTIVE = "prod"
    }
  }

  ephemeral_storage {
    size = "512"
  }

  function_name                  = "demoForum-dev-demoForum"
  handler                        = "com.demo.demoforum.LambdaHandler::handleRequest"
  memory_size                    = "2048"
  package_type                   = "Zip"
  reserved_concurrent_executions = "-1"
  role                           = "arn:aws:iam::752417200383:role/demoForum-dev-ap-northeast-2-lambdaRole"
  runtime                        = "java11"
  source_code_hash               = "f5FkqyhRQSwqhOFdpA/BcmpKGWNBQSZt9xb4rAIcBPg="

  tags = {
    STAGE = "dev"
  }

  tags_all = {
    STAGE = "dev"
  }

  timeout = "60"

  tracing_config {
    mode = "Active"
  }
}
