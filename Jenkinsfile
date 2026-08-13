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
		SONAR_HOST          = credentials('sonarqube-host')
        SONAR_TOKEN         = credentials('sonarqube-token')
        SONAR_PROJECT_KEY   = credentials('sonarqube-project-key')
        SONAR_PROJECT_NAME  = credentials('sonarqube-project-name')
	}

	stages {
	    stage('Clean Up') {
            steps {
                echo 'Cleaning Up ...'
                cleanWs()
                echo 'Cleaned Up'
            }
        }

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
                    sh "docker build -t ${IMAGE_NAME}-base:${IMAGE_TAG} --target base ."
                }

                echo 'Built'
            }
        }

//		stage('Test') {
//		    parallel {
//                stage('Unit Tests') {
//                    steps {
//                        echo 'Running Unit Tests ...'
//
//                        script {
//                            sh "docker build -t books-back-end-unit-tests:${IMAGE_TAG} --target unit-tests ."
//                            sh "docker create --name books-back-end-unit-tests-${IMAGE_TAG} books-back-end-unit-tests:${IMAGE_TAG}"
//                            sh "mkdir -p ${TEST_RESULTS_DIR}"
//                            sh "docker cp books-back-end-unit-tests-${IMAGE_TAG}:/app/build/reports/tests/unit-tests ./${TEST_RESULTS_DIR}/unit-tests"
//                            sh "cd ./${TEST_RESULTS_DIR}/unit-tests/ && zip -r ../unit-tests.zip ./*"
//                        }
//
//                        echo 'Unit Tests Complete'
//                    }
//
//                    post {
//                        always {
//                            sh "docker rm books-back-end-unit-tests-${IMAGE_TAG}"
//                            sh "docker rmi -f books-back-end-unit-tests:${IMAGE_TAG}"
//                        }
//                    }
//                }
//
//                stage('SpotBugs Tests') {
//                    steps {
//                        echo 'Running SpotBugs Tests ...'
//
//                        script {
//                            sh "docker build -t books-back-end-spotbugs-tests:${IMAGE_TAG} --target spotbugs-tests ."
//                            sh "docker create --name books-back-end-spotbugs-tests-${IMAGE_TAG} books-back-end-spotbugs-tests:${IMAGE_TAG}"
//                            sh "mkdir -p ${TEST_RESULTS_DIR}"
//                            sh "docker cp books-back-end-spotbugs-tests-${IMAGE_TAG}:/app/build/reports/spotbugs ./${TEST_RESULTS_DIR}/spotbugs-tests"
//                            sh "cd ./${TEST_RESULTS_DIR}/spotbugs-tests/ && zip -r ../spotbugs-tests.zip ./*"
//                        }
//
//                        echo 'SpotBugs Tests Complete'
//                    }
//
//                    post {
//                        always {
//                            sh "docker rm books-back-end-spotbugs-tests-${IMAGE_TAG}"
//                            sh "docker rmi -f books-back-end-spotbugs-tests:${IMAGE_TAG}"
//                        }
//                    }
//                }
//            }
//		}

		stage('SonarQube Analysis') {
            steps {
                echo 'Running SonarQube Tests ...'

                withSonarQubeEnv('sonarqube') {
                    script {
                        sh "docker build -t books-back-end-sonarqube-tests:${IMAGE_TAG} --target sonarqube-tests --build-arg SONAR_HOST=${SONAR_HOST} --build-arg SONAR_TOKEN=${SONAR_TOKEN} --build-arg SONAR_PROJECT_KEY=${SONAR_PROJECT_KEY} --build-arg SONAR_PROJECT_NAME=${SONAR_PROJECT_NAME} ."
//                        sh "docker create --name books-back-end-spotbugs-tests-${IMAGE_TAG} books-back-end-spotbugs-tests:${IMAGE_TAG}"
                        sh "mkdir -p ${TEST_RESULTS_DIR}"
                        sh "mkdir -p ${TEST_RESULTS_DIR}/sonarqube-tests"
                        sh "curl -H \"Authorization: Bearer ${SONAR_TOKEN}\" ${SONAR_HOST}/api/qualitygates/project_status?projectKey=${SONAR_PROJECT_KEY} > ${TEST_RESULTS_DIR}/sonarqube-tests/sq-quality-gate-status.json"
//                        sh "docker cp books-back-end-spotbugs-tests-${IMAGE_TAG}:/app/build/reports/spotbugs ./${TEST_RESULTS_DIR}/spotbugs-tests"
                        sh "cd ./${TEST_RESULTS_DIR}/sonarqube-tests/ && zip -r ../sonarqube-tests.zip ./*"
                    }
                }

                echo 'SonarQube Tests Complete'
            }
        }

		stage('Assemble') {
            steps {
                echo 'Assembling ...'

                script {
                    sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} --target runtime --build-arg BUILD_VERSION=${BUILD_VERSION} --build-arg BUILD_DATE=\"${BUILD_DATE}\" ."
                }

                echo 'Assembled'
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
        success {
            cleanWs()

            sh "docker rmi -f ${IMAGE_NAME}-base:${IMAGE_TAG}"
            sh "docker rmi -f ${IMAGE_NAME}:${IMAGE_TAG}"
            sh "docker rmi -f ${DOCKER_HUB_USER}/${IMAGE_NAME}:${IMAGE_TAG}"
            sh "docker rmi -f ${DOCKER_HUB_USER}/${IMAGE_NAME}:latest"
        }

		always {
			sh 'docker logout'

			archiveArtifacts artifacts: "${TEST_RESULTS_DIR}/**.zip", allowEmptyArchive: true
		}
	}
}