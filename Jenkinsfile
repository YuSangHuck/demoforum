pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                    url: 'https://github.com/YuSangHuck/demoforum.git'
            }
        }
        stage('printenv') {

            steps {
                sh 'printenv'
            }
        }
    }
}