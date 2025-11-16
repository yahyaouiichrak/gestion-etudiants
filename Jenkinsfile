pipeline {
  agent any
  environment {
    JAVA_HOME = tool 'jdk17'
    MVN_HOME  = tool 'maven-3.9'
    PATH = "${JAVA_HOME}/bin:${MVN_HOME}/bin:${env.PATH}"
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
    stage('Build JAR') {
      steps {
        sh 'mvn -B -DskipTests clean package'
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
    success { echo "✅ Image poussée: ${DOCKER_IMAGE}:${IMAGE_TAG}" }
    failure { echo "❌ Échec Étape 1" }
  }
}