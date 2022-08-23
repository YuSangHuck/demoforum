#terraform {
#  backend "s3" {
#    bucket         = "tf-demo-forum-state"
#    key            = "terraform.tfstate"
#    encrypt        = true
#    dynamodb_table = "tf-demo-forum-state-lock"
#  }
#}
