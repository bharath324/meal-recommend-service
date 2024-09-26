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
    stage('Remove old containers pattern name') {
      agent any
      steps {
        sh 'docker stop $(docker ps -a --filter \'name=sapient\' -q) || true'
        sh 'docker rm $(docker ps -a --filter \'name=sapient\' -q) || true'
      }
    }
    stage('Run the meal service') {
      agent any
      steps {
          sh 'docker run -p 8088:8080 --name bharath-sapient-meal-service-${BUILD_NUMBER} -d bharath3244/bharath-sapient-mealservice:latest'
      }
    }
  }
}