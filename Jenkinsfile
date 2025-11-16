pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "ichrakyhy/gestion-etudiants"
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
                script {
                    shortSha = sh(returnStdout: true, script: "git rev-parse --short HEAD").trim()
                    env.IMAGE_TAG = "${env.BUILD_NUMBER}-${shortSha}"
                }
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Build JAR') {
            steps {
                sh 'mvn -B clean package'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Docker Build') {
            steps {
                sh """
                  docker build \
                    -t ${DOCKER_IMAGE}:${IMAGE_TAG} \
                    -t ${DOCKER_IMAGE}:latest .
                """
            }
        }

        stage('Docker Push') {
            environment { DOCKERHUB_CREDS = credentials('dockerhub-creds') }
            steps {
                sh '''
                  echo "${DOCKERHUB_CREDS_PSW}" | docker login -u "${DOCKERHUB_CREDS_USR}" --password-stdin
                  docker push ${DOCKER_IMAGE}:${IMAGE_TAG}
                  docker push ${DOCKER_IMAGE}:latest
                  docker logout
                '''
            }
        }

        stage('Deploy Locally') {
            steps {
                sh '''
                  docker-compose down || true
                  docker-compose up -d
                '''
            }
        }
    }

    post {
        success { echo "✅ Pipeline terminé avec succès" }
        failure { echo "❌ Échec du pipeline" }
    }
}