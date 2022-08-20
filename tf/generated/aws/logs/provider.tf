provider "aws" {}

terraform {
	required_providers {
		aws = {
	    version = "~> 4.27.0"
		}
  }
}
