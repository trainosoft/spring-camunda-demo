node {
    stage 'Clone the project'
    git 'https://github.com/eugenp/tutorials.git'
  
    dir('spring-jenkins-pipeline') {
        stage("Compilation and Analysis") {
            parallel 'Compilation': {
                sh "./mvnw clean install -DskipTests"
            }
        }
        
        stage("Tests and Deployment") {
            stage("Staging") 
                    sh 'nohup ./mvnw spring-boot:run -Dserver.port=8989 &'
                }  
            }
        }
    }
}
