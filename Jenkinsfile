node {
  stage('SCM-Checkout'){
    git 'https://github.com/trainosoft/spring-camunda-demo.git'
  }
  
  stage('Compile-package'){
    sh "./mvnw clean install -DskipTests"
  }
  
  
}