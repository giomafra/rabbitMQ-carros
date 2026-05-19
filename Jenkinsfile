pipeline {
    agent any

    environment {
        IMAGE_NAME = "spring-api"
        CONTAINER_NAME = "spring-container"
    }

    stages {

        stage('Build') {
            steps {
                sh './mvnw clean package'
            }
        }

        stage('Tests') {
            steps {
                sh './mvnw test'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t $IMAGE_NAME .'
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                docker stop $CONTAINER_NAME || true
                docker rm $CONTAINER_NAME || true

                docker run -d \
                    --name $CONTAINER_NAME \
                    -p 8081:8080 \
                    $IMAGE_NAME
                '''
            }
        }
    }
}