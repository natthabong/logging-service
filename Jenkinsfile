def GIT_REPOSITORY_REPO = "http://gitlab.gec.io/gecscf/logging-service.git"
pipeline {
  agent { node { label 'gecscf-unix-001' } }
  stages {
    stage('[SCM] Checkout API service') {
      steps {
        echo "Pulling from ${git_branch}"
        git branch: '${git_branch}', credentialsId: '28413f37-4882-46c8-9b30-6530cc145bed', url: GIT_REPOSITORY_REPO
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
        sh "mvn sonar:sonar -P sonar-coverage -Dsonar.host.url=http://${sonar_host}:9000 -Dsonar.junit.reportPaths=target/surefire-reports -Dsonar.analysis.buildNumber=${BUILD_NUMBER}"
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