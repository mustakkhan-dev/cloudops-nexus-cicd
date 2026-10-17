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
                withSonarQubeEnv('sonar-server') {
                    sh "${scannerHome}/bin/sonar-scanner"
                }
            }
        }
        stage("Quality Gate") {
            steps {
                script {
                    echo "Checking Sonar Quality Gate..."
                    // Wait with abortOnError or allow pipeline to proceed
                    timeout(time: 2, unit: 'MINUTES') {
                        def qg = waitForQualityGate(abortPipeline: false)
                        echo "Quality Gate status: ${qg.status}"
                    }
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
        stage("Docker Publish") {
            steps {
                script {
                    echo '<--------------- Docker Build & Tag Completed --------------->'  
                    // Image is built locally on the agent ready for EKS deployment!
                    echo "Docker image built: ${imageName}:${version}"
                }
            }
        }
                }
            }
        }

        stage("Deploy") {
            steps {
                script {
                    echo '<--------------- Helm Deploy Started --------------->'
                    sh 'helm upgrade --install sample-app ./sample-app --namespace sample-app --create-namespace'
                    echo '<--------------- Helm Deploy Ends --------------->'
                }
            }
        }  
    }
}
