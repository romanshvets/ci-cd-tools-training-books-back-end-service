pipeline {
	agent any

	stages {
		stage('Checkout') {
			steps {
				echo 'Checking out ...'
				checkout scm
				echo 'Checked out !'
			}
		}

		stage('Build') {
			steps {
				echo 'Building ...'

				script {
					sh "docker build -t books-back-service:${env.BUILD_ID} ."

					sh "docker tag books-back-service:${env.BUILD_ID} rshvets89/books-back-service:${env.BUILD_ID}"
					sh "docker tag books-back-service:${env.BUILD_ID} rshvets89/books-back-service:latest"
				}

				echo 'Built !'
			}
		}

		stage('Deploy') {
			steps {
				echo 'Deploying ...'

				script {
					docker.withRegistry('https://docker.io', 'dockerhub-credentials') {
						sh "docker push rshvets89/books-back-service:${env.BUILD_ID}"
						sh "docker push rshvets89/books-back-service:latest"
					}
				}

				echo 'Deployed !'
			}
		}
	}
}