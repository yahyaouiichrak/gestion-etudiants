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
                    def shortSha = sh(returnStdout: true, script: "git rev-parse --short HEAD").trim()
                    env.IMAGE_TAG = "${env.BUILD_NUMBER}-${shortSha}"
                }
            }
        }

        stage('Start PostgreSQL for tests') {
            steps {
                sh '''
                docker run -d --name pg-test \
                    -e POSTGRES_USER=postgres \
                    -e POSTGRES_PASSWORD=0000 \
                    -e POSTGRES_DB=etudiantdb \
                    -p 5433:5432 postgres:15
                sleep 10
                '''
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn -Dspring.profiles.active=test test'
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
                  docker build -t ${DOCKER_IMAGE}:${IMAGE_TAG} -t ${DOCKER_IMAGE}:latest .
                """
            }
        }

        stage('Docker Push') {
            environment {
                DOCKERHUB_CREDS = credentials('dockerhub-creds')
            }
            steps {
                sh '''
                  echo "${DOCKERHUB_CREDS_PSW}" | docker login -u "${DOCKERHUB_CREDS_USR}" --password-stdin
                  docker push ${DOCKER_IMAGE}:${IMAGE_TAG}
                  docker push ${DOCKER_IMAGE}:latest
                  docker logout
                '''
            }
        }
    }

    post {
        always {
            sh "docker rm -f pg-test || true"
        }
        success { echo "✅ Pipeline terminé avec succès" }
        failure { echo "❌ Échec du pipeline" }
    }
}
