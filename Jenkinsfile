String ERROR = 'error'
String FAILURE = 'failure'
String PENDING = 'pending'
String SUCCESS = 'success'

void setBuildStatus(String message, String state) {
    def backref = createBackref()

    step([
            $class             : "GitHubCommitStatusSetter",
            reposSource        : [$class: "ManuallyEnteredRepositorySource", url: "https://github.com/YuSangHuck/demoforum"],
            contextSource      : [$class: "ManuallyEnteredCommitContextSource", context: "ci/jenkins/build-status"], // github에서 식별자. branch-rule, commit-status
            errorHandlers      : [[$class: "ChangingBuildStatusErrorHandler", result: "UNSTABLE"]], // 어디에 쓰이는건지 모름
//            statusResultSource: [ $class: "ConditionalStatusResultSource", results: [[$class: "AnyBuildResult", message: message, state: state]]], // 결과 보여줌
            statusBackrefSource: [$class: "ManuallyEnteredBackrefSource", backref: backref]
    ])
}

void setBuildStatusWithSha(String message, String state, String sha) {
    def backref = createBackref()

    step([
            $class             : "GitHubCommitStatusSetter",
            commitShaSource    : [$class: 'ManuallyEnteredShaSource', 'sha': sha],
            reposSource        : [$class: "ManuallyEnteredRepositorySource", url: "https://github.com/YuSangHuck/demoforum"],
            contextSource      : [$class: "ManuallyEnteredCommitContextSource", context: "ci/jenkins/build-status"], // github에서 식별자. branch-rule, commit-status
            errorHandlers      : [[$class: "ChangingBuildStatusErrorHandler", result: "UNSTABLE"]], // 어디에 쓰이는건지 모름
            statusResultSource : [$class: "ConditionalStatusResultSource", results: [[$class: "AnyBuildResult", message: message, state: state]]], // 결과 보여줌
            statusResultSource : [
                    $class : 'ConditionalStatusResultSource',
                    results: [
                            [$class: 'BetterThanOrEqualBuildResult', result: 'SUCCESS', state: 'SUCCESS', message: 'success'],
                            [$class: 'BetterThanOrEqualBuildResult', result: 'FAILURE', state: 'FAILURE', message: 'failure'],
                            [$class: 'AnyBuildResult', state: 'FAILURE', message: 'Loophole']
                    ]
            ],
            statusBackrefSource: [$class: "ManuallyEnteredBackrefSource", backref: backref]
    ])
}

String createBackref() {
    String PUBLIC_JENKINS = 'http://svsrrmnzb2ckyh4576v4saelh1xrdq49.iptime.org:10101'
    def host = new URI(PUBLIC_JENKINS).getHost()
    String RUN_DISPLAY_URL = "${env.RUN_DISPLAY_URL}"
    def publcJenkisUri = new URI(RUN_DISPLAY_URL)
//    def authority = publcJenkisUri.getAuthority() // localhost:10101
    def fragment = publcJenkisUri.getFragment() // null
//    def host = publcJenkisUri.getHost() // localhost
    def path = publcJenkisUri.getPath() // /job/demoforum/job/feature%2Fcicd/25/display/redirect
    def port = publcJenkisUri.getPort() // 10101
    def query = publcJenkisUri.getQuery() // null
    def scheme = publcJenkisUri.getScheme() // http
    def userInfo = publcJenkisUri.getUserInfo() // null

    return new URI(scheme, userInfo, host, port, path, query, fragment).toString()
}

pipeline {
    agent any
    tools {
//        terraform 'terraform'
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
//                sh 'printenv'
//                echo currentBuild.result // SUCCESS, UNSTABLE, FAILURE
//                echo currentBuild.currentResult
//                echo currentBuild.resultIsBetterOrEqualTo('SUCCESS')
//                echo currentBuild.resultIsBetterOrEqualTo('UNSTABLE')
//                echo currentBuild.resultIsBetterOrEqualTo('FAILURE')
            }
            post {
                script {
                    currentBuild.result = FAILURE
                }
            }
        }
    }
    post {
//        success {
//            echo 'success'
//            setBuildStatus("Build complete", SUCCESS)
//        }
//        failure {
//            echo 'fail'
//            setBuildStatus("Build failed", FAILURE)
//        }
        always {
            echo 'I will always execute this'
            echo currentBuild.currentResult
//            setBuildStatusWithSha("Build ERROR", ERROR, '1dece08dda9afc26e6a6c3ae071ded439e9c0e16')
//            setBuildStatusWithSha("Build FAILURE", FAILURE, '6e0e1e3bf32f4bd5260e95e7af649ed8d37dd4da')
//            setBuildStatusWithSha("Build PENDING", PENDING, '4b671037c4de728d6e7064ccfa160d784fe0cb8b')
//            setBuildStatusWithSha("Build SUCCESS", SUCCESS, 'b51de836792020381a7da654b50d0102f07f11cd')
        }
    }
}
