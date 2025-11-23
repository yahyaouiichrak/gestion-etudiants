
pipeline {
    agent any

    environment {
        SONAR_TOKEN = credentials('sonar-token')
        DOCKER_IMAGE = "ichrakyhy/gestion-etudiants"
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'master', url: 'https://github.com/yahyaouiichrak/gestion-etudiants.git'
            }
        }

        stage('Build for Sonar') {
            steps {
                sh 'mvn -B clean verify -DskipTests'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonar-server') {
                    sh """
                        mvn sonar:sonar \
                        -Dsonar.projectKey=gestion-etudiants \
                        -Dsonar.projectName=gestion-etudiants \
                        -Dsonar.host.url=http://10.0.0.2:9000 \
                        -Dsonar.token=${SONAR_TOKEN} \
                        -Dsonar.java.binaries=target/classes
                    """
                }
            }
        }

        stage('Trivy - File System Scan') {
            steps {
                sh 'trivy fs --skip-db-update --scanners vuln --exit-code 0 --severity HIGH,CRITICAL .'
            }
        }

        stage('Start PostgreSQL for tests') {
            steps {
                sh 'docker run -d --name pg-test -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=0000 -e POSTGRES_DB=etudiantdb -p 5433:5432 postgres:15'
                sh 'sleep 10'
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
                script {
                    def GIT_COMMIT = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
                    sh "docker build -t ${DOCKER_IMAGE}:${BUILD_NUMBER}-${GIT_COMMIT} -t ${DOCKER_IMAGE}:latest ."
                }
            }
        }

      
        stage('Trivy - Docker Image Scan') {
            steps {
                script {
                    // Vérifier si la base Java existe
                    def javaDbExists = sh(script: 'test -d ~/.cache/trivy/java-db', returnStatus: true) == 0
                    if (!javaDbExists) {
                        echo "Java DB not found. Downloading..."
                        sh 'trivy --download-java-db'
                    }
                    sh """
                        trivy image --skip-db-update --skip-java-db-update \
                        --scanners vuln --exit-code 0 --severity HIGH,CRITICAL ${DOCKER_IMAGE}:latest
                    """
                }
            }
        }

    }

    post {
        always {
            sh 'docker rm -f pg-test || true'
        }
    }
}
