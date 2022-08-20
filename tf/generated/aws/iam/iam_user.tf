resource "aws_iam_user" "tfer--AIDA26L4PLD7RCAGTKVVT" {
  force_destroy = "false"
  name          = "admin"
  path          = "/"
}

resource "aws_iam_user" "tfer--AIDA26L4PLD7T32BQLUL2" {
  force_destroy = "false"
  name          = "yusanghuck"
  path          = "/"
}

resource "aws_iam_user" "tfer--AIDA26L4PLD7VVF5BZ7VD" {
  force_destroy = "false"
  name          = "jenkins-local"
  path          = "/"

  tags = {
    env  = "local"
    role = "ci"
  }

  tags_all = {
    env  = "local"
    role = "ci"
  }
}
