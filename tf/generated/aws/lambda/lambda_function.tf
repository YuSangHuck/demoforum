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
  source_code_hash               = "z3IuWkxq63HnpLlo3OHzCkO6RLKjgHclKMEJvdbwus8="

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

resource "aws_lambda_function" "tfer--tf-lambda-function" {
  environment {
    variables = {
      SPRING_PROFILES_ACTIVE = "prod"
    }
  }

  ephemeral_storage {
    size = "512"
  }

  function_name                  = "tf-lambda-function"
  handler                        = "com.demo.demoforum.LambdaHandler::handleRequest"
  memory_size                    = "2048"
  package_type                   = "Zip"
  reserved_concurrent_executions = "-1"
  role                           = "arn:aws:iam::752417200383:role/tf-lambda-iam"
  runtime                        = "java11"
  source_code_hash               = "u9I4l40nIM0mOx52ELjCz0tUwvQlaX1fOaxCG1TIbH8="

  tags = {
    CREATED_BY = "tf"
  }

  tags_all = {
    CREATED_BY = "tf"
  }

  timeout = "60"

  tracing_config {
    mode = "Active"
  }
}
