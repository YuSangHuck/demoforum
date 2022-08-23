## S3 bucket for backend
#resource "aws_s3_bucket" "tfstate" {
#  bucket = "${local.prefix}-state"
#}
#
#resource "aws_s3_bucket_versioning" "versioning" {
#  bucket = aws_s3_bucket.tfstate.id
#  versioning_configuration {
#    status = "Enabled"
#  }
#}
#
## DynamoDB for terraform state lock
#resource "aws_dynamodb_table" "terraform_state_lock" {
#  name         = "${local.prefix}-state-lock"
#  hash_key     = "LockID"
#  billing_mode = "PAY_PER_REQUEST"
#
#  attribute {
#    name = "LockID"
#    type = "S"
#  }
#}