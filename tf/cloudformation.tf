#resource "aws_cloudformation_stack" "tf-demo-forum-stack" {
#  capabilities     = ["CAPABILITY_IAM", "CAPABILITY_NAMED_IAM"]
#  disable_rollback = "false"
#  name             = "${local.prefix}-cloudformation-stack"
#
#  tags = merge(
#    {
#      NAME = "${local.prefix}-cloudformation-stack"
#    },
#    local.common_tags
#  )
#  template_body      = "{\"AWSTemplateFormatVersion\":\"2010-09-09\",\"Description\":\"The AWS CloudFormation template for this Serverless application\",\"Outputs\":{\"DemoForumLambdaFunctionQualifiedArn\":{\"Description\":\"Current Lambda function version\",\"Export\":{\"Name\":\"sls-demoForum-dev-DemoForumLambdaFunctionQualifiedArn\"},\"Value\":{\"Ref\":\"DemoForumLambdaVersionmNF2U6znoucGEeJFSSVpraH5gvWfoR36ekAGMMl0Lc\"}},\"HttpApiId\":{\"Description\":\"Id of the HTTP API\",\"Export\":{\"Name\":\"sls-demoForum-dev-HttpApiId\"},\"Value\":{\"Ref\":\"HttpApi\"}},\"HttpApiUrl\":{\"Description\":\"URL of the HTTP API\",\"Export\":{\"Name\":\"sls-demoForum-dev-HttpApiUrl\"},\"Value\":{\"Fn::Join\":[\"\",[\"https://\",{\"Ref\":\"HttpApi\"},\".execute-api.\",{\"Ref\":\"AWS::Region\"},\".\",{\"Ref\":\"AWS::URLSuffix\"}]]}},\"ServerlessDeploymentBucketName\":{\"Export\":{\"Name\":\"sls-demoForum-dev-ServerlessDeploymentBucketName\"},\"Value\":{\"Ref\":\"ServerlessDeploymentBucket\"}}},\"Resources\":{\"DemoForumLambdaFunction\":{\"DependsOn\":[\"DemoForumLogGroup\"],\"Properties\":{\"Code\":{\"S3Bucket\":{\"Ref\":\"ServerlessDeploymentBucket\"},\"S3Key\":\"serverless/demoForum/dev/1661088331588-2022-08-21T13:25:31.588Z/build/distributions/demoForum-0.0.1-SNAPSHOT.zip\"},\"Environment\":{\"Variables\":{\"SPRING_PROFILES_ACTIVE\":\"prod\"}},\"FunctionName\":\"demoForum-dev-demoForum\",\"Handler\":\"com.demo.demoforum.LambdaHandler::handleRequest\",\"MemorySize\":2048,\"Role\":{\"Fn::GetAtt\":[\"IamRoleLambdaExecution\",\"Arn\"]},\"Runtime\":\"java11\",\"Timeout\":60,\"TracingConfig\":{\"Mode\":\"Active\"}},\"Type\":\"AWS::Lambda::Function\"},\"DemoForumLambdaPermissionHttpApi\":{\"Properties\":{\"Action\":\"lambda:InvokeFunction\",\"FunctionName\":{\"Fn::GetAtt\":[\"DemoForumLambdaFunction\",\"Arn\"]},\"Principal\":\"apigateway.amazonaws.com\",\"SourceArn\":{\"Fn::Join\":[\"\",[\"arn:\",{\"Ref\":\"AWS::Partition\"},\":execute-api:\",{\"Ref\":\"AWS::Region\"},\":\",{\"Ref\":\"AWS::AccountId\"},\":\",{\"Ref\":\"HttpApi\"},\"/*\"]]}},\"Type\":\"AWS::Lambda::Permission\"},\"DemoForumLambdaVersionmNF2U6znoucGEeJFSSVpraH5gvWfoR36ekAGMMl0Lc\":{\"DeletionPolicy\":\"Retain\",\"Properties\":{\"CodeSha256\":\"z3IuWkxq63HnpLlo3OHzCkO6RLKjgHclKMEJvdbwus8=\",\"FunctionName\":{\"Ref\":\"DemoForumLambdaFunction\"}},\"Type\":\"AWS::Lambda::Version\"},\"DemoForumLogGroup\":{\"Properties\":{\"LogGroupName\":\"/aws/lambda/demoForum-dev-demoForum\"},\"Type\":\"AWS::Logs::LogGroup\"},\"HttpApi\":{\"Properties\":{\"Name\":\"dev-demoForum\",\"ProtocolType\":\"HTTP\"},\"Type\":\"AWS::ApiGatewayV2::Api\"},\"HttpApiIntegrationDemoForum\":{\"Properties\":{\"ApiId\":{\"Ref\":\"HttpApi\"},\"IntegrationType\":\"AWS_PROXY\",\"IntegrationUri\":{\"Fn::GetAtt\":[\"DemoForumLambdaFunction\",\"Arn\"]},\"PayloadFormatVersion\":\"1.0\",\"TimeoutInMillis\":30000},\"Type\":\"AWS::ApiGatewayV2::Integration\"},\"HttpApiLogGroup\":{\"Properties\":{\"LogGroupName\":\"/aws/http-api/demoForum-dev\"},\"Type\":\"AWS::Logs::LogGroup\"},\"HttpApiRouteDefault\":{\"DependsOn\":\"HttpApiIntegrationDemoForum\",\"Properties\":{\"ApiId\":{\"Ref\":\"HttpApi\"},\"RouteKey\":\"$default\",\"Target\":{\"Fn::Join\":[\"/\",[\"integrations\",{\"Ref\":\"HttpApiIntegrationDemoForum\"}]]}},\"Type\":\"AWS::ApiGatewayV2::Route\"},\"HttpApiStage\":{\"DependsOn\":\"HttpApiLogGroup\",\"Properties\":{\"AccessLogSettings\":{\"DestinationArn\":{\"Fn::GetAtt\":[\"HttpApiLogGroup\",\"Arn\"]},\"Format\":\"{ \\\"requestId\\\": \\\"$context.requestId\\\", \\\"ip\\\": \\\"$context.identity.sourceIp\\\", \\\"requestTime\\\": \\\"$context.requestTime\\\", \\\"httpMethod\\\": \\\"$context.httpMethod\\\", \\\"routeKey\\\": \\\"$context.routeKey\\\", \\\"status\\\": \\\"$context.status\\\", \\\"protocol\\\": \\\"$context.protocol\\\", \\\"responseLength\\\": \\\"$context.responseLength\\\", \\\"path\\\": \\\"$context.path\\\", \\\"basePathMatched\\\": \\\"$context.customDomain.basePathMatched\\\" }\"},\"ApiId\":{\"Ref\":\"HttpApi\"},\"AutoDeploy\":true,\"DefaultRouteSettings\":{\"DetailedMetricsEnabled\":false},\"StageName\":\"$default\"},\"Type\":\"AWS::ApiGatewayV2::Stage\"},\"IamRoleLambdaExecution\":{\"Properties\":{\"AssumeRolePolicyDocument\":{\"Statement\":[{\"Action\":[\"sts:AssumeRole\"],\"Effect\":\"Allow\",\"Principal\":{\"Service\":[\"lambda.amazonaws.com\"]}}],\"Version\":\"2012-10-17\"},\"Path\":\"/\",\"Policies\":[{\"PolicyDocument\":{\"Statement\":[{\"Action\":[\"logs:CreateLogStream\",\"logs:CreateLogGroup\"],\"Effect\":\"Allow\",\"Resource\":[{\"Fn::Sub\":\"arn:$${AWS::Partition}:logs:$${AWS::Region}:$${AWS::AccountId}:log-group:/aws/lambda/demoForum-dev*:*\"}]},{\"Action\":[\"logs:PutLogEvents\"],\"Effect\":\"Allow\",\"Resource\":[{\"Fn::Sub\":\"arn:$${AWS::Partition}:logs:$${AWS::Region}:$${AWS::AccountId}:log-group:/aws/lambda/demoForum-dev*:*:*\"}]},{\"Action\":[\"xray:PutTraceSegments\",\"xray:PutTelemetryRecords\"],\"Effect\":\"Allow\",\"Resource\":[\"*\"]}],\"Version\":\"2012-10-17\"},\"PolicyName\":{\"Fn::Join\":[\"-\",[\"demoForum\",\"dev\",\"lambda\"]]}}],\"RoleName\":{\"Fn::Join\":[\"-\",[\"demoForum\",\"dev\",{\"Ref\":\"AWS::Region\"},\"lambdaRole\"]]}},\"Type\":\"AWS::IAM::Role\"},\"ServerlessDeploymentBucket\":{\"Properties\":{\"BucketEncryption\":{\"ServerSideEncryptionConfiguration\":[{\"ServerSideEncryptionByDefault\":{\"SSEAlgorithm\":\"AES256\"}}]}},\"Type\":\"AWS::S3::Bucket\"},\"ServerlessDeploymentBucketPolicy\":{\"Properties\":{\"Bucket\":{\"Ref\":\"ServerlessDeploymentBucket\"},\"PolicyDocument\":{\"Statement\":[{\"Action\":\"s3:*\",\"Condition\":{\"Bool\":{\"aws:SecureTransport\":false}},\"Effect\":\"Deny\",\"Principal\":\"*\",\"Resource\":[{\"Fn::Join\":[\"\",[\"arn:\",{\"Ref\":\"AWS::Partition\"},\":s3:::\",{\"Ref\":\"ServerlessDeploymentBucket\"},\"/*\"]]},{\"Fn::Join\":[\"\",[\"arn:\",{\"Ref\":\"AWS::Partition\"},\":s3:::\",{\"Ref\":\"ServerlessDeploymentBucket\"}]]}]}]}},\"Type\":\"AWS::S3::BucketPolicy\"}}}"
#  timeout_in_minutes = "0"
#}
#
#
#
#beautify_template_body = {
#  "AWSTemplateFormatVersion": "2010-09-09",
#  "Description": "The AWS CloudFormation template for this Serverless application",
#  "Outputs": {
#    "DemoForumLambdaFunctionQualifiedArn": {
#      "Description": "Current Lambda function version",
#      "Export": {
#        "Name": "sls-demoForum-dev-DemoForumLambdaFunctionQualifiedArn"
#      },
#      "Value": {
#        "Ref": "DemoForumLambdaVersionmNF2U6znoucGEeJFSSVpraH5gvWfoR36ekAGMMl0Lc"
#      }
#    },
#    "HttpApiId": {
#      "Description": "Id of the HTTP API",
#      "Export": {
#        "Name": "sls-demoForum-dev-HttpApiId"
#      },
#      "Value": {
#        "Ref": "HttpApi"
#      }
#    },
#    "HttpApiUrl": {
#      "Description": "URL of the HTTP API",
#      "Export": {
#        "Name": "sls-demoForum-dev-HttpApiUrl"
#      },
#      "Value": {
#        "Fn::Join": [
#          "",
#          [
#            "https://",
#            {
#              "Ref": "HttpApi"
#            },
#            ".execute-api.",
#            {
#              "Ref": "AWS::Region"
#            },
#            ".",
#            {
#              "Ref": "AWS::URLSuffix"
#            }
#          ]
#        ]
#      }
#    },
#    "ServerlessDeploymentBucketName": {
#      "Export": {
#        "Name": "sls-demoForum-dev-ServerlessDeploymentBucketName"
#      },
#      "Value": {
#        "Ref": "ServerlessDeploymentBucket"
#      }
#    }
#  },
#  "Resources": {
#    "DemoForumLambdaFunction": {
#      "DependsOn": [
#        "DemoForumLogGroup"
#      ],
#      "Properties": {
#        "Code": {
#          "S3Bucket": {
#            "Ref": "ServerlessDeploymentBucket"
#          },
#          "S3Key": "serverless/demoForum/dev/1661088331588-2022-08-21T13:25:31.588Z/build/distributions/demoForum-0.0.1-SNAPSHOT.zip"
#        },
#        "Environment": {
#          "Variables": {
#            "SPRING_PROFILES_ACTIVE": "prod"
#          }
#        },
#        "FunctionName": "demoForum-dev-demoForum",
#        "Handler": "com.demo.demoforum.LambdaHandler::handleRequest",
#        "MemorySize": 2048,
#        "Role": {
#          "Fn::GetAtt": [
#            "IamRoleLambdaExecution",
#            "Arn"
#          ]
#        },
#        "Runtime": "java11",
#        "Timeout": 60,
#        "TracingConfig": {
#          "Mode": "Active"
#        }
#      },
#      "Type": "AWS::Lambda::Function"
#    },
#    "DemoForumLambdaPermissionHttpApi": {
#      "Properties": {
#        "Action": "lambda:InvokeFunction",
#        "FunctionName": {
#          "Fn::GetAtt": [
#            "DemoForumLambdaFunction",
#            "Arn"
#          ]
#        },
#        "Principal": "apigateway.amazonaws.com",
#        "SourceArn": {
#          "Fn::Join": [
#            "",
#            [
#              "arn:",
#              {
#                "Ref": "AWS::Partition"
#              },
#              ":execute-api:",
#              {
#                "Ref": "AWS::Region"
#              },
#              ":",
#              {
#                "Ref": "AWS::AccountId"
#              },
#              ":",
#              {
#                "Ref": "HttpApi"
#              },
#              "/*"
#            ]
#          ]
#        }
#      },
#      "Type": "AWS::Lambda::Permission"
#    },
#    "DemoForumLambdaVersionmNF2U6znoucGEeJFSSVpraH5gvWfoR36ekAGMMl0Lc": {
#      "DeletionPolicy": "Retain",
#      "Properties": {
#        "CodeSha256": "z3IuWkxq63HnpLlo3OHzCkO6RLKjgHclKMEJvdbwus8=",
#        "FunctionName": {
#          "Ref": "DemoForumLambdaFunction"
#        }
#      },
#      "Type": "AWS::Lambda::Version"
#    },
#    "DemoForumLogGroup": {
#      "Properties": {
#        "LogGroupName": "/aws/lambda/demoForum-dev-demoForum"
#      },
#      "Type": "AWS::Logs::LogGroup"
#    },
#    "HttpApi": {
#      "Properties": {
#        "Name": "dev-demoForum",
#        "ProtocolType": "HTTP"
#      },
#      "Type": "AWS::ApiGatewayV2::Api"
#    },
#    "HttpApiIntegrationDemoForum": {
#      "Properties": {
#        "ApiId": {
#          "Ref": "HttpApi"
#        },
#        "IntegrationType": "AWS_PROXY",
#        "IntegrationUri": {
#          "Fn::GetAtt": [
#            "DemoForumLambdaFunction",
#            "Arn"
#          ]
#        },
#        "PayloadFormatVersion": "1.0",
#        "TimeoutInMillis": 30000
#      },
#      "Type": "AWS::ApiGatewayV2::Integration"
#    },
#    "HttpApiLogGroup": {
#      "Properties": {
#        "LogGroupName": "/aws/http-api/demoForum-dev"
#      },
#      "Type": "AWS::Logs::LogGroup"
#    },
#    "HttpApiRouteDefault": {
#      "DependsOn": "HttpApiIntegrationDemoForum",
#      "Properties": {
#        "ApiId": {
#          "Ref": "HttpApi"
#        },
#        "RouteKey": "$default",
#        "Target": {
#          "Fn::Join": [
#            "/",
#            [
#              "integrations",
#              {
#                "Ref": "HttpApiIntegrationDemoForum"
#              }
#            ]
#          ]
#        }
#      },
#      "Type": "AWS::ApiGatewayV2::Route"
#    },
#    "HttpApiStage": {
#      "DependsOn": "HttpApiLogGroup",
#      "Properties": {
#        "AccessLogSettings": {
#          "DestinationArn": {
#            "Fn::GetAtt": [
#              "HttpApiLogGroup",
#              "Arn"
#            ]
#          },
#          "Format": "{ someFormat }"
#        },
#        "ApiId": {
#          "Ref": "HttpApi"
#        },
#        "AutoDeploy": true,
#        "DefaultRouteSettings": {
#          "DetailedMetricsEnabled": false
#        },
#        "StageName": "$default"
#      },
#      "Type": "AWS::ApiGatewayV2::Stage"
#    },
#    "IamRoleLambdaExecution": {
#      "Properties": {
#        "AssumeRolePolicyDocument": {
#          "Statement": [
#            {
#              "Action": [
#                "sts:AssumeRole"
#              ],
#              "Effect": "Allow",
#              "Principal": {
#                "Service": [
#                  "lambda.amazonaws.com"
#                ]
#              }
#            }
#          ],
#          "Version": "2012-10-17"
#        },
#        "Path": "/",
#        "Policies": [
#          {
#            "PolicyDocument": {
#              "Statement": [
#                {
#                  "Action": [
#                    "logs:CreateLogStream",
#                    "logs:CreateLogGroup"
#                  ],
#                  "Effect": "Allow",
#                  "Resource": [
#                    {
#                      "Fn::Sub": "arn:$${AWS::Partition}:logs:$${AWS::Region}:$${AWS::AccountId}:log-group:/aws/lambda/demoForum-dev*:*"
#                    }
#                  ]
#                },
#                {
#                  "Action": [
#                    "logs:PutLogEvents"
#                  ],
#                  "Effect": "Allow",
#                  "Resource": [
#                    {
#                      "Fn::Sub": "arn:$${AWS::Partition}:logs:$${AWS::Region}:$${AWS::AccountId}:log-group:/aws/lambda/demoForum-dev*:*:*"
#                    }
#                  ]
#                },
#                {
#                  "Action": [
#                    "xray:PutTraceSegments",
#                    "xray:PutTelemetryRecords"
#                  ],
#                  "Effect": "Allow",
#                  "Resource": [
#                    "*"
#                  ]
#                }
#              ],
#              "Version": "2012-10-17"
#            },
#            "PolicyName": {
#              "Fn::Join": [
#                "-",
#                [
#                  "demoForum",
#                  "dev",
#                  "lambda"
#                ]
#              ]
#            }
#          }
#        ],
#        "RoleName": {
#          "Fn::Join": [
#            "-",
#            [
#              "demoForum",
#              "dev",
#              {
#                "Ref": "AWS::Region"
#              },
#              "lambdaRole"
#            ]
#          ]
#        }
#      },
#      "Type": "AWS::IAM::Role"
#    },
#    "ServerlessDeploymentBucket": {
#      "Properties": {
#        "BucketEncryption": {
#          "ServerSideEncryptionConfiguration": [
#            {
#              "ServerSideEncryptionByDefault": {
#                "SSEAlgorithm": "AES256"
#              }
#            }
#          ]
#        }
#      },
#      "Type": "AWS::S3::Bucket"
#    },
#    "ServerlessDeploymentBucketPolicy": {
#      "Properties": {
#        "Bucket": {
#          "Ref": "ServerlessDeploymentBucket"
#        },
#        "PolicyDocument": {
#          "Statement": [
#            {
#              "Action": "s3:*",
#              "Condition": {
#                "Bool": {
#                  "aws:SecureTransport": false
#                }
#              },
#              "Effect": "Deny",
#              "Principal": "*",
#              "Resource": [
#                {
#                  "Fn::Join": [
#                    "",
#                    [
#                      "arn:",
#                      {
#                        "Ref": "AWS::Partition"
#                      },
#                      ":s3:::",
#                      {
#                        "Ref": "ServerlessDeploymentBucket"
#                      },
#                      "/*"
#                    ]
#                  ]
#                },
#                {
#                  "Fn::Join": [
#                    "",
#                    [
#                      "arn:",
#                      {
#                        "Ref": "AWS::Partition"
#                      },
#                      ":s3:::",
#                      {
#                        "Ref": "ServerlessDeploymentBucket"
#                      }
#                    ]
#                  ]
#                }
#              ]
#            }
#          ]
#        }
#      },
#      "Type": "AWS::S3::BucketPolicy"
#    }
#  }
#}