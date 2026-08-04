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
				script {
					sh "docker build -t books-back-service:${env.BUILD_ID}"
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