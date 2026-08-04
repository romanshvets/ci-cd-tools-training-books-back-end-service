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
				stage('Build Docker Image') {
					steps {
						script {
							// Looks for a file named 'Dockerfile' in the root workspace folder
							def appImage = docker.build("books-back-service:${env.BUILD_ID}")

							// Optional: Push to a registry if needed later
							// appImage.push()
						}
					}
				}
			}
		}

		//stage('Build') {
		//	steps {
		//		echo 'Building ...'
		//		checkout scm
		//
		//	}
		//}
		//stage('Test') {
		//	steps {
		//		echo 'Testing ...'
		//	}
		//}
		//stage('Deploy') {
		//	steps {
		//		echo 'Deploying ...'
		//	}
		//}
	}
}