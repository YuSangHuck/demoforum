locals {
  prefix = "tf-demo-forum"

  common_tags = {
    STAGE = "dev"
    PROJECT     = "demo-forum"
    CREATED_BY  = "tf"
  }
}