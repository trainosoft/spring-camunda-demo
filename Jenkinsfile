node {
  stage('SCM-Checkout'){
    git 'https://github.com/trainosoft/spring-camunda-demo.git'
  }
  
  stage('Compile-package'){
    def mvnHome = tool name: 'Jenkins-maven', type: 'maven'
    sh "${mvnHome}/bin/mvn package"
  }
  
  
}