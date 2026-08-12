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
                            sh "mkdir -p ${TEST_RESULTS_DIR}"
                            sh "docker cp books-back-end-unit-tests-${IMAGE_TAG}:/app/build/reports/tests/unit-tests ./${TEST_RESULTS_DIR}/unit-tests"
                            sh "cd ./${TEST_RESULTS_DIR}/unit-tests/ && zip -r ../unit-tests.zip ./*"
                        }

                        echo 'Unit Tests Complete.'
                    }

                    post {
                        always {
                            sh "docker rm books-back-end-unit-tests-${IMAGE_TAG}"
                            sh "docker rmi -f books-back-end-unit-tests:${IMAGE_TAG}"
                        }
                    }
                }

                stage('PMD Tests') {
                    steps {
                        echo 'Running PMD Tests ...'

                        script {
                            sh "docker build -t books-back-end-pmd-tests:${IMAGE_TAG} --target pmd-tests ."
                            sh "docker run --name books-back-end-pmd-tests-${IMAGE_TAG} books-back-end-pmd-tests:${IMAGE_TAG}"
                            sh "mkdir -p ${TEST_RESULTS_DIR}"
                            sh "docker cp books-back-end-pmd-tests-${IMAGE_TAG}:/app/build/reports/pmd ./${TEST_RESULTS_DIR}/pmd-tests"
                            sh "cd ./${TEST_RESULTS_DIR}/pmd-tests/ && zip -r ../pmd-tests.zip ./*"
                        }

                        echo 'SpotBugs PMD Complete.'
                    }

                    post {
                        always {
                            sh "docker rm books-back-end-pmd-tests-${IMAGE_TAG}"
                            sh "docker rmi -f books-back-end-pmd-tests:${IMAGE_TAG}"
                        }
                    }
                }

                stage('SpotBugs Tests') {
                    steps {
                        echo 'Running SpotBugs Tests ...'

                        script {
                            sh "docker build -t books-back-end-spotbugs-tests:${IMAGE_TAG} --target spotbugs-tests ."
                            sh "docker run --name books-back-end-spotbugs-tests-${IMAGE_TAG} books-back-end-spotbugs-tests:${IMAGE_TAG}"
                            sh "mkdir -p ${TEST_RESULTS_DIR}"
                            sh "docker cp books-back-end-spotbugs-tests-${IMAGE_TAG}:/app/build/reports/spotbugs ./${TEST_RESULTS_DIR}/spotbugs-tests"
                            sh "cd ./${TEST_RESULTS_DIR}/spotbugs-tests/ && zip -r ../spotbugs-tests.zip ./*"
                        }

                        echo 'SpotBugs Tests Complete ...'
                    }

                    post {
                        always {
                            sh "docker rm books-back-end-spotbugs-tests-${IMAGE_TAG}"
                            sh "docker rmi -f books-back-end-spotbugs-tests:${IMAGE_TAG}"
                        }
                    }
                }
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
					    sh "docker tag ${IMAGE_NAME}:${IMAGE_TAG} ${DOCKER_HUB_USER}/${IMAGE_NAME}:${IMAGE_TAG}"
                        sh "docker tag ${IMAGE_NAME}:${IMAGE_TAG} ${DOCKER_HUB_USER}/${IMAGE_NAME}:latest"

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

			archiveArtifacts artifacts: "${TEST_RESULTS_DIR}/**.zip", allowEmptyArchive: true
		}
	}
}