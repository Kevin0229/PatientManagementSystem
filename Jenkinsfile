pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven'
    }

    stages {
        stage('Clone Repository') {
            steps {
                git url: 'https://github.com/Goutham-24/PatientManagementSystem.git', branch: 'main'
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