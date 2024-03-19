pipeline{

    agent any

    environment {
        DATE = new Date().format('yy.M')
        TAG = "${DATE}.${BUILD_NUMBER}"
    }

    stages{

        stage('Build Jar'){

            agent{
                docker{
                    image 'maven:3.9.3-eclipse-temurin-17-focal'
                    args "-u root -v //C://Users//lohan//.m2//:/root/.m2"
                }
            }


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
                    docker.withRegistry('https://registry.hub.docker.com','lohanadocker-creds'){
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