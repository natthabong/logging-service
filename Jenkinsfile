def GIT_REPOSITORY_REPO = "http://gitlab.gec.io/gecscf/logging-service.git"
pipeline {
  agent { node { label 'gecscf-unix-001' } }
  stages {
    stage('[SCM] Checkout API service') {
      steps {
        echo "Pulling from ${git_branch}"
        git branch: '${git_branch}', credentialsId: '28413f37-4882-46c8-9b30-6530cc145bed', url: GIT_REPOSITORY_REPO
        script {
            GIT_COMMIT_EMAIL = sh (
                script: 'git --no-pager show -s --format=\'%ae\'',
                returnStdout: true
            ).trim()
        }
      }
    }
    stage('[MAVEN] Pack sources') {
      steps {
        sh "mvn clean install -P ${profile}"
      }
      post {
        always { 
	       junit 'target/surefire-reports/*.xml'
	    }
      }
    }
    stage('[SONAR] Analyzing code quality') {
      when { expression { return params.sonar_host?.trim() } }
      steps {
        sh "mvn sonar:sonar -Dsonar.host.url=http://${sonar_host}:9000 -Dsonar.junit.reportPaths=target/surefire-reports -Dsonar.analysis.buildNumber=${BUILD_NUMBER} -Dsonar.analysis.author=${GIT_COMMIT_EMAIL}"
      }
    }
    stage('[DOCKER] Build an image') {
      steps {
        sh "docker build -t logging-service:${image_tag} - < target/logging-service-0.1-SNAPSHOT-bin.tar.gz"
        sh "docker tag logging-service:${image_tag} registry-gecscf.gec.io:5000/logging-service:${image_tag}"
      }
    }
    stage('[DOCKER] Shift an image to private registry') {
      steps {
        sh 'docker login registry-gecscf.gec.io:5000 -u gecscf -p gecscf123!'
        sh "docker push registry-gecscf.gec.io:5000/logging-service:${image_tag}"
      }
    }
  }
  post { 
    always { 
       junit 'target/surefire-reports/*.xml'
    }
    success {
       build "${downstream_job}"
    }
  }
}