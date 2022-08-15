pipeline {
    agent any
    tools {
        terraform 'terraform'
    }
    environment {
        AWS_ACCESS_KEY_ID = credentials('AWS_ACCESS_KEY_ID')
        AWS_SECRET_ACCESS_KEY = credentials('AWS_SECRET_ACCESS_KEY')
        AWS_REGION = credentials('AWS_REGION')
    }
    stages {
        stage('TF version') {
            steps {
                sh 'terraform --version'
            }
        }
        stage('TF Init&Plan') {
            steps {
                dir('tf') {
                    sh 'terraform init'
                    sh 'terraform plan'
                }
            }
        }
        stage('Approval') {
            steps {
                script {
                    def userInput = input(id: 'confirm', message: 'Apply Terraform?', parameters: [ [$class: 'BooleanParameterDefinition', defaultValue: false, description: 'Apply terraform', name: 'confirm'] ])
                }
            }
        }
        stage('TF Apply') {
            steps {
                dir('tf') {
                    sh 'terraform apply -input=false -auto-approve'
                }
            }
        }
        stage('TF Destroy'){
            steps {
                dir('tf') {
                    sh 'terraform destroy -auto-approve'
                }
            }
        }
    }
}