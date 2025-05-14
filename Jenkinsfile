pipeline {
    agent any

    tools {
        jdk 'JDK19'
        maven 'Maven'
    }

    stages {
        stage('Clone Repository') {
            steps {
                git url: 'https://github.com/Kevin0229/PatientManagementSystem.git', branch: 'main'
            }
        }

        stage('Build and Test') {
            steps {
                bat 'java -version'  
                bat 'mvn clean test'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build('patient-system')
                }
            }
        }
    }
}