locals {
  prefix = "tf-demo-forum"

  common_tags = {
    ENVIRONMENT = "dev"
    PROJECT     = "demo-forum"
    CREATED_BY  = "tf"
  }
}