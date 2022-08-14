pipeline {
    agent any
    environment {
        AWS_ACCESS_KEY_ID = credentials('AWS_ACCESS_KEY_ID')
        AWS_SECRET_ACCESS_KEY = credentials('AWS_SECRET_ACCESS_KEY')
        REGION = credentials('AWS_REGION')
    }
    stages {
        stage('printenv') {
            steps {
                sh 'printenv'
            }
        }
        stage('echo') {
            steps {
                echo "AWS_ACCESS_KEY_ID is ${AWS_ACCESS_KEY_ID}"
                echo "AWS_SECRET_ACCESS_KEY is ${AWS_SECRET_ACCESS_KEY}"
                echo "REGION is ${REGION}"
            }
        }
        stage('TF Init&Plan') {
            steps {
                sh 'terraform init'
                sh 'terraform plan'
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
                sh 'terraform apply -input=false'
            }
        }
    }
}