pipeline {
    agent any

    tools {
        maven 'Maven3'
    }

    triggers {
        cron('H 2 * * *')    // runs nightly around 2 AM
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Run Tests') {
            steps {
                bat 'mvn clean test'
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'reports/**, screenshots/**', allowEmptyArchive: true
        }
    }
}