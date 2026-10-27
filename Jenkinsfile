pipeline {
    agent any

    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'],
               description: 'Browser to run the tests on')
        booleanParam(name: 'HEADLESS', defaultValue: false,
               description: 'Run without opening a browser window')
        choice(name: 'SUITE', choices: ['testng.xml', 'smoke.xml'],
               description: 'testng.xml = regression, smoke.xml = smoke')
        string(name: 'URL', defaultValue: 'https://www.saucedemo.com/',
               description: 'Application URL (environment)')
    }

    tools {
        maven 'Maven3'
    }

    triggers {
        cron('H 2 * * *')        // runs nightly around 2 AM
        pollSCM('H/5 * * * *')   // checks GitHub every 5 mins for new commits
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Run Tests') {
            steps {
                // double quotes so Groovy fills in ${params.X}
                bat "mvn clean test -Dbrowser=${params.BROWSER} -Dheadless=${params.HEADLESS} -DsuiteXmlFile=${params.SUITE} -Durl=${params.URL}"
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
