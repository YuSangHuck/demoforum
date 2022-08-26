String ERROR = 'error'
String FAILURE = 'failure'
String PENDING = 'pending'
String SUCCESS = 'success'

void setBuildStatus(String message, String state) {
    step([
            $class             : "GitHubCommitStatusSetter",
            reposSource        : [$class: "ManuallyEnteredRepositorySource", url: "https://github.com/YuSangHuck/demoforum"],
            contextSource      : [$class: "ManuallyEnteredCommitContextSource", context: "ci/jenkins/build-status"], // github에서 식별자. branch-rule, commit-status
            errorHandlers      : [$class: "ChangingBuildStatusErrorHandler", result: "UNSTABLE"], // 어디에 쓰이는건지 모름
            statusResultSource : [$class: "ConditionalStatusResultSource", results: [[$class: "AnyBuildResult", message: message, state: state]]]//, // 결과 보여줌
//            statusBackrefSource: [$class: "ManuallyEnteredBackrefSource", backref: "http://svsrrmnzb2ckyh4576v4saelh1xrdq49.iptime.org:10101"]  // 뒤에 /blue/organizations/jenkins/demoforum/detail/feature%2Fcicd/14/pipeline 이런거 붙어야 함
    ]);
}

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
        stage('setStatusSuccess') {
            steps {
                echo 'setStatusSuccess'
                sh 'printenv'
            }
        }
    }
    post {
        success {
            echo 'success'
            setBuildStatus("Build complete", SUCCESS);
        }
        failure {
            echo 'fail'
            setBuildStatus("Build failed", FAILURE);
        }
        always {
            echo 'I will always execute this'
        }
    }
}

//
//- setBuildStatus 테스트
//인자별 모든 케이스 구해서 각 케이스 별 sha 임의로 긁어서 jenkis에서 jenkinsfile 수정 후 run해서 github에서 어케나오는지 보기
//failure인거 왜 그런지 확인