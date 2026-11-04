#!/bin/bash
set -e
echo "=========================================="
echo "Starting Jenkins Master Automated Setup..."
echo "=========================================="
sudo yum update -y
sudo wget -O /etc/yum.repos.d/corretto.repo https://yum.corretto.aws/corretto.repo
sudo yum install -y java-21-amazon-corretto-devel fontconfig freetype git
sudo wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/redhat-stable/jenkins.repo
sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io-2023.key
sudo yum install -y jenkins
sudo systemctl enable jenkins
sudo systemctl start jenkins

echo "Waiting for Jenkins to generate initial admin password..."
sleep 20
echo "=========================================="
echo "JENKINS READY! YOUR ADMIN PASSWORD IS:"
sudo cat /var/lib/jenkins/secrets/initialAdminPassword
echo "=========================================="
