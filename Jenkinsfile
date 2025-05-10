pipeline {
    agent any

    tools {
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