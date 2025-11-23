pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "ichrakyhy/gestion-etudiants"
        SONAR_TOKEN = credentials('sonar-token')
    }

    stages {

        // 1️⃣ Checkout
        stage('Checkout Code') {
            steps {
                checkout scm
                script {
                    def shortSha = sh(returnStdout: true, script: "git rev-parse --short HEAD").trim()
                    env.IMAGE_TAG = "${env.BUILD_NUMBER}-${shortSha}"
                }
            }
        }

        // 2️⃣ Build Maven (obligatoire AVANT Sonar)
        stage('Build for Sonar') {
            steps {
                sh """
                    mvn -B clean verify -DskipTests
                """
            }
        }

        // 3️⃣ Analyse SonarQube
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonar-server') {
                    sh """
                        mvn sonar:sonar \
                        -Dsonar.projectKey=gestion-etudiants \
                        -Dsonar.projectName=gestion-etudiants \
                        -Dsonar.host.url=http://10.0.0.2:9000 \
                        -Dsonar.login=$SONAR_TOKEN \
                        -Dsonar.java.binaries=target/classes
                    """
                }
            }
        }

        // 4️⃣ Trivy FS
        stage('Trivy - File System Scan') {
            steps {
                sh """
                    trivy fs --exit-code 0 --severity HIGH,CRITICAL . > trivy-fs-report.txt
                """
                archiveArtifacts artifacts: 'trivy-fs-report.txt', fingerprint: true
            }
        }

        // 5️⃣ Start PostgreSQL
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

        // 6️⃣ Run Tests
        stage('Run Tests') {
            steps {
                sh 'mvn -Dspring.profiles.active=test test'
            }
        }

        // 7️⃣ Build JAR
        stage('Build JAR') {
            steps {
                sh 'mvn -B clean package'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        // 8️⃣ Build Docker
        stage('Docker Build') {
            steps {
                sh """
                  docker build -t ${DOCKER_IMAGE}:${IMAGE_TAG} -t ${DOCKER_IMAGE}:latest .
                """
            }
        }

        // 9️⃣ Trivy Image
        stage('Trivy - Docker Image Scan') {
            steps {
                sh """
                    trivy image --exit-code 0 --severity HIGH,CRITICAL ${DOCKER_IMAGE}:${IMAGE_TAG} > trivy-image-report.txt
                """
                archiveArtifacts artifacts: 'trivy-image-report.txt', fingerprint: true
            }
        }

        // 🔟 Docker Push
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

    // 🔚 Post
    post {
        always {
            sh "docker rm -f pg-test || true"
        }
        success { echo "✅ Pipeline terminé avec succès" }
        failure { echo "❌ Échec du pipeline" }
    }
}
