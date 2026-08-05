pipeline {
	agent any

	environment {
		DOCKER_HUB_USER = 'rshvets89'
		IMAGE_NAME      = 'books-back-service'
		IMAGE_TAG       = "${env.BUILD_NUMBER}"
	}

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
					sh "docker tag ${DOCKER_HUB_USER}/${IMAGE_NAME}:${IMAGE_TAG} ${DOCKER_HUB_USER}/${IMAGE_NAME}:latest"


					sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."

					sh "docker tag ${IMAGE_NAME}:${IMAGE_TAG} ${DOCKER_HUB_USER}/${IMAGE_NAME}:${IMAGE_TAG}"
					sh "docker tag ${IMAGE_NAME}:${IMAGE_TAG} ${DOCKER_HUB_USER}/${IMAGE_NAME}:latest"
				}

				echo 'Built !'
			}
		}

		//stage('Push to Docker Hub') {
		//	steps {
		//		// Securely binds the credentials ID you saved in Step 2
		//		withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials',
		//			usernameVariable: 'DOCKER_USER',
		//			passwordVariable: 'DOCKER_PASS')]) {
		//			script {
		//				// Log into Docker Hub securely using the variables
		//				sh "echo \$DOCKER_PASS | docker login -u \$DOCKER_USER --password-stdin"
		//
		//				// Push both the specific build tag and the latest tag
		//				sh "docker push ${DOCKER_HUB_USER}/${IMAGE_NAME}:${IMAGE_TAG}"
		//				sh "docker push ${DOCKER_HUB_USER}/${IMAGE_NAME}:latest"
		//			}
		//		}
		//	}
		//}
		//
		//stage('Deploy') {
		//	steps {
		//		echo 'Deploying ...'
		//
		//		script {
		//			docker.withRegistry('https://docker.io', 'dockerhub-credentials') {
		//				sh "docker push rshvets89/books-back-service:${env.BUILD_ID}"
		//				sh "docker push rshvets89/books-back-service:latest"
		//			}
		//		}
		//
		//		echo 'Deployed !'
		//	}
		//}
	}

	post {
		always {
			sh "docker logout"
		}
	}
}