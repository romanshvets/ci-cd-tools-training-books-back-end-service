pipeline {
	agent {
		label 'build-agent'
	}

    environment {
		DOCKER_HUB_USER     = 'rshvets89'
		IMAGE_NAME          = 'books-back-service'
		IMAGE_TAG           = "${env.BUILD_NUMBER}"
		BUILD_VERSION       = "${env.BUILD_NUMBER}"
		BUILD_DATE          = "${new Date().format('yyyy-MM-dd HH:mm:ss')}"
		TEST_RESULTS_DIR    = 'test-results'
	}

	stages {
		stage('Checkout') {
			steps {
				echo 'Checking out ...'
				checkout scm
				echo 'Checked out'
			}
		}

		stage('Build') {
			steps {
				echo 'Building ...'

				script {
					sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} --target runtime --build-arg BUILD_VERSION=${BUILD_VERSION} --build-arg BUILD_DATE=\"${BUILD_DATE}\" ."

					sh "docker tag ${IMAGE_NAME}:${IMAGE_TAG} ${DOCKER_HUB_USER}/${IMAGE_NAME}:${IMAGE_TAG}"
					sh "docker tag ${IMAGE_NAME}:${IMAGE_TAG} ${DOCKER_HUB_USER}/${IMAGE_NAME}:latest"
				}

				echo 'Built'
			}
		}

		stage('Test') {
		    parallel {
                stage('Unit Tests') {
                    steps {
                        echo 'Running Unit Tests ...'

                        script {
                            sh "docker build -t books-back-end-unit-tests:${IMAGE_TAG} --target unit-tests ."
                            sh "docker run --name books-back-end-unit-tests-${IMAGE_TAG} books-back-end-unit-tests:${IMAGE_TAG}"
                            sh "docker cp books-back-end-unit-tests-${IMAGE_TAG}:/app/build/reports/tests ./${TEST_RESULTS_DIR}/"

// docker build --target unit-tests -t books-back-end-unit-tests:1 .
// docker run --name books-back-end-unit-tests-1 books-back-end-unit-tests:1
// docker cp books-back-end-unit-tests-1:/app/build/reports/tests ./host-results/

                        }

                        echo 'Unit tests complete ...'
                    }

//                    post {
//                        always {
//                        }
//                    }
                }


//                stage('Test On Linux') {
//                    agent {
//                        label "linux"
//                    }
//                    steps {
//                        sh "run-tests.sh"
//                    }
//                    post {
//                        always {
//                            junit "**/TEST-*.xml"
//                        }
//                    }
//                }
            }
		}

		stage('Deploy') {
			steps {
				echo 'Deploying ...'

				withCredentials([
					usernamePassword(
						credentialsId: 'dockerhub-credentials',
						usernameVariable: 'DOCKER_USER',
						passwordVariable: 'DOCKER_PASS'
					)]) {

					script {
						sh "echo \$DOCKER_PASS | docker login -u \$DOCKER_USER --password-stdin"

						sh "docker push ${DOCKER_HUB_USER}/${IMAGE_NAME}:${IMAGE_TAG}"
						sh "docker push ${DOCKER_HUB_USER}/${IMAGE_NAME}:latest"
					}
				}

				echo 'Deployed'
			}
		}
	}

	post {
		always {
			sh 'docker logout'
		}
	}
}