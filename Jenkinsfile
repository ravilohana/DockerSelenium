pipeline{

    agent any

    stages{

        stage('Build Jar'){
            steps{
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build Image'){
            steps{
                bat 'docker build -t=ravilohana/selenium-docker:latest .'
            }
        }

        stage('Push Image'){
            environment{
                DOCKER_HUB = credentials('lohanadocker-creds')
            }
            steps{
                bat '"echo user: ${DOCKER_HUB_USR} ==== password: ${DOCKER_HUB_PSW} "'
//                bat 'docker login -u ${DOCKER_HUB_USR} -p{DOCKER_HUB_PSW}'
                bat 'docker login -u ${DOCKER_HUB_USR} --password-stdin'
                bat 'echo ${DOCKER_HUB_PSW} | docker login -u ${DOCKER_HUB_USR} --password-stdin'
                bat "docker push ravilohana/selenium-docker:latest"
            }
        }
    }
    post{
        always{
            bat 'docker logout'
        }
    }
}