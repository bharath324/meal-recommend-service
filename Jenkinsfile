pipeline {
  agent none
  stages {
    stage('Maven Install') {
      agent {
        docker {
          image 'maven:3.9.9'
          args '-u root'
        }
      }
      steps {
        sh 'mvn clean install'
      }
    }
    stage('Docker Build') {
      agent any
      steps {
        sh 'docker build -t bharath3244/bharath-sapient-mealservice:latest .'
      }
    }
  }
}