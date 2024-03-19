pipeline{

    agent any

    environment {
        DATE = new Date().format('yy.M')
        TAG = "${DATE}.${BUILD_NUMBER}"
    }

    stages{

        stage('Build Jar'){
            steps{
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build Image'){
            steps{
                script{
                    docker.build("ravilohana/selenium-docker:${TAG}")
                }
            }
        }

        stage('Push Image'){
            steps{

                script{
                    docker.withRegistry('','lohanadocker-creds'){
                        docker.image("ravilohana/selenium-docker:${TAG}").push()
                        docker.image("ravilohana/selenium-docker:${TAG}").push("latest")
                    }

                }
            }
        }
    }
    post{
        always{
            bat 'docker logout'
        }
    }
}