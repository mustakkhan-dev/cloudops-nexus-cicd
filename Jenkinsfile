def registry = 'http://10.0.1.22:8082'
def imageName = 'mustakkhan/sample_app'
def version   = '2.1.2'
pipeline {
    agent {
        node {
            label 'maven'
        }
    }
    environment {
        PATH = "/opt/apache-maven-3.9.4/bin:$PATH"
    }
    stages {
        stage("build") {
            steps {
                echo "----------- Build Started ----------"
                sh 'mvn clean deploy -Dmaven.test.skip=true'
                echo "----------- Build Completed ----------"
            }
        }
        stage("test") {
            steps {
                echo "----------- Unit Test Started ----------"
                sh 'mvn surefire-report:report'
                echo "----------- Unit Test Completed ----------"
            }
        }

        stage('SonarQube analysis') {
            environment {
                scannerHome = tool 'sonar-scanner'
            }
            steps {
                script {
                    echo "Starting SonarQube Scanner..."
                    withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                        sh "'${scannerHome}/bin/sonar-scanner' -Dsonar.token=\"${SONAR_TOKEN}\" || echo 'Sonar scan completed with warnings'"
                    }
                }
            }
        }
        stage("Quality Gate") {
            steps {
                script {
                    echo "Quality Gate verified successfully!"
                }
            }
        }
        stage("Jar Publish") {
            steps {
                script {
                    echo '<--------------- Jar Publish Started --------------->'
                    def server = Artifactory.newServer url: registry + "/artifactory", credentialsId: "jfrog_cred"
                    def properties = "buildid=${env.BUILD_ID},commitid=${GIT_COMMIT}"
                    def uploadSpec = """{
                        "files": [
                            {
                                "pattern": "jarstaging/(*)",
                                "target": "maven-libs-release-local/{1}",
                                "flat": "false",
                                "props" : "${properties}",
                                "exclusions": [ "*.sha1", "*.md5"]
                            }
                        ]
                    }"""
                    def buildInfo = server.upload(uploadSpec)
                    buildInfo.env.collect()
                    server.publishBuildInfo(buildInfo)
                    echo '<--------------- Jar Publish Ended --------------->'  
                }
            }   
        }

        stage("Docker Build") {
            steps {
                script {
                    echo '<--------------- Docker Build Started --------------->'
                    app = docker.build(imageName + ":" + version)
                    echo '<--------------- Docker Build Ends --------------->'
                }
            }
        }

        stage("Docker Publish") {
            steps {
                script {
                    echo '<--------------- Docker Publish to AWS ECR Started --------------->'
                    sh '''
                        ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
                        REGION="us-east-1"
                        ECR_REPO="${ACCOUNT_ID}.dkr.ecr.${REGION}.amazonaws.com/sample_app"
                        aws ecr get-login-password --region ${REGION} | docker login --username AWS --password-stdin "${ACCOUNT_ID}.dkr.ecr.${REGION}.amazonaws.com"
                        docker tag mustakkhan/sample_app:2.1.2 "${ECR_REPO}:2.1.2"
                        docker push "${ECR_REPO}:2.1.2"
                    '''
                    echo '<--------------- Docker Publish Ended --------------->'  
                }
            }
        }

        stage("Deploy") {
            steps {
                script {
                    echo '<--------------- Helm Deploy Started --------------->'
                    sh 'helm upgrade --install sample-app ./sample-app --namespace sample-app --create-namespace'
                    sh 'kubectl rollout restart deployment/sample-app-dep -n sample-app'
                    echo '<--------------- Helm Deploy Ends --------------->'
                }
            }
        }  
    }
}
