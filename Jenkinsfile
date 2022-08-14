pipeline {
    agent any
    tools {
//        terraform 'Terraform 1.2.7 linux'
//        terraform
//        terraform 'Terraform'
//        terraform 'Terraform 1.2.7'
//        terraform 'Terraform 1.2.7 linux'
//        terraform 'Terraform 1.2.7 linux'
//        terraform 'Terraform 1.2.7 linux'
//        terraform '1.2.7'
//        terraform '1.2.7 linux'
//        terraform 'Terraform 1.2.7'
        maven '3.8.6'
    }
    environment {
        AWS_ACCESS_KEY_ID = credentials('AWS_ACCESS_KEY_ID')
        AWS_SECRET_ACCESS_KEY = credentials('AWS_SECRET_ACCESS_KEY')
        REGION = credentials('AWS_REGION')
    }
    stages {
        stage('TF version') {
            steps {
                sh 'terraform --version'
            }
        }
//        stage('TF Init&Plan') {
//            steps {
//                sh 'terraform init'
//                sh 'terraform plan'
//            }
//        }
//        stage('Approval') {
//            steps {
//                script {
//                    def userInput = input(id: 'confirm', message: 'Apply Terraform?', parameters: [ [$class: 'BooleanParameterDefinition', defaultValue: false, description: 'Apply terraform', name: 'confirm'] ])
//                }
//            }
//        }
//        stage('TF Apply') {
//            steps {
//                sh 'terraform apply -input=false'
//            }
//        }
    }
}

//
//[ant
//, hudson.tasks.Ant$AntInstallation
//, dockerTool
//, org.jenkinsci.plugins.docker.commons.tools.DockerTool
//, git
//, hudson.plugins.git.GitTool
//, gradle
//, hudson.plugins.gradle.GradleInstallation
//, jdk
//, hudson.model.JDK
//, jgit
//, org.jenkinsci.plugins.gitclient.JGitTool
//, jgitapache
//, org.jenkinsci.plugins.gitclient.JGitApacheTool
//, maven
//, hudson.tasks.Maven$MavenInstallation
//, terraform
//, org.jenkinsci.plugins.terraform.TerraformInstallation]