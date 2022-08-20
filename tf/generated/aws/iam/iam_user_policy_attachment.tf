resource "aws_iam_user_policy_attachment" "tfer--admin_AdministratorAccess" {
  policy_arn = "arn:aws:iam::aws:policy/AdministratorAccess"
  user       = "admin"
}

resource "aws_iam_user_policy_attachment" "tfer--admin_IAMUserChangePassword" {
  policy_arn = "arn:aws:iam::aws:policy/IAMUserChangePassword"
  user       = "admin"
}

resource "aws_iam_user_policy_attachment" "tfer--jenkins-local_AWSBillingConductorFullAccess" {
  policy_arn = "arn:aws:iam::aws:policy/AWSBillingConductorFullAccess"
  user       = "jenkins-local"
}

resource "aws_iam_user_policy_attachment" "tfer--jenkins-local_AdministratorAccess" {
  policy_arn = "arn:aws:iam::aws:policy/AdministratorAccess"
  user       = "jenkins-local"
}

resource "aws_iam_user_policy_attachment" "tfer--yusanghuck_AWSBillingConductorFullAccess" {
  policy_arn = "arn:aws:iam::aws:policy/AWSBillingConductorFullAccess"
  user       = "yusanghuck"
}

resource "aws_iam_user_policy_attachment" "tfer--yusanghuck_AdministratorAccess" {
  policy_arn = "arn:aws:iam::aws:policy/AdministratorAccess"
  user       = "yusanghuck"
}
