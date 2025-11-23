pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "ichrakyhy/gestion-etudiants"
    }

    stages {

        // ----------------------------------
        // 1️⃣ Checkout code & définir IMAGE_TAG
        // ----------------------------------
        stage('Checkout Code') {
            steps {
                checkout scm
                script {
                    def shortSha = sh(returnStdout: true, script: "git rev-parse --short HEAD").trim()
                    env.IMAGE_TAG = "${env.BUILD_NUMBER}-${shortSha}"
                }
            }
        }

        // ----------------------------------
        // 2️⃣ SonarQube : Analyse qualité du code
        // ----------------------------------
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonar-server') {
                    sh """
                        mvn sonar:sonar \
                          -Dsonar.projectKey=gestion-etudiants \
                          -Dsonar.projectName=Gestion Etudiants \
                          -Dsonar.host.url=$SONAR_HOST_URL \
                          -Dsonar.login=$SONAR_AUTH_TOKEN
                    """
                }
            }
        }

        // ----------------------------------
        // 3️⃣ Trivy : Scan code source
        // ----------------------------------
        stage('Trivy - File System Scan') {
            steps {
                sh """
                    trivy fs --exit-code 0 --severity HIGH,CRITICAL . > trivy-fs-report.txt
                """
                archiveArtifacts artifacts: 'trivy-fs-report.txt', fingerprint: true
            }
        }

        // ----------------------------------
        // 4️⃣ Démarrer PostgreSQL pour tests
        // ----------------------------------
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

        // ----------------------------------
        // 5️⃣ Run Tests Maven
        // ----------------------------------
        stage('Run Tests') {
            steps {
                sh 'mvn -Dspring.profiles.active=test test'
            }
        }

        // ----------------------------------
        // 6️⃣ Build JAR Maven
        // ----------------------------------
        stage('Build JAR') {
            steps {
                sh 'mvn -B clean package'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        // ----------------------------------
        // 7️⃣ Build Docker image
        // ----------------------------------
        stage('Docker Build') {
            steps {
                sh """
                  docker build -t ${DOCKER_IMAGE}:${IMAGE_TAG} -t ${DOCKER_IMAGE}:latest .
                """
            }
        }

        // ----------------------------------
        // 8️⃣ Trivy : Scan Docker image
        // ----------------------------------
        stage('Trivy - Docker Image Scan') {
            steps {
                sh """
                    trivy image --exit-code 0 --severity HIGH,CRITICAL ${DOCKER_IMAGE}:${IMAGE_TAG} > trivy-image-report.txt
                """
                archiveArtifacts artifacts: 'trivy-image-report.txt', fingerprint: true
            }
        }

        // ----------------------------------
        // 9️⃣ Push Docker image
        // ----------------------------------
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

    // ----------------------------------
    // Post-actions
    // ----------------------------------
    post {
        always {
            sh "docker rm -f pg-test || true"
        }
        success { echo "✅ Pipeline terminé avec succès" }
        failure { echo "❌ Échec du pipeline" }
    }
}
