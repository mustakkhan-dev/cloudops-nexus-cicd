#!/bin/bash
set -e
echo "=========================================="
echo "Starting Jenkins Agent Tools Setup..."
echo "=========================================="
sudo yum update -y
sudo yum install -y docker git
sudo systemctl enable --now docker
sudo usermod -aG docker ec2-user

# Java 21
sudo wget -O /etc/yum.repos.d/corretto.repo https://yum.corretto.aws/corretto.repo
sudo yum install -y java-21-amazon-corretto-devel fontconfig freetype

# Maven 3.9.4
sudo wget -O /tmp/maven.tar.gz https://archive.apache.org/dist/maven/maven-3/3.9.4/binaries/apache-maven-3.9.4-bin.tar.gz
sudo tar -xzf /tmp/maven.tar.gz -C /opt/
sudo ln -sf /opt/apache-maven-3.9.4/bin/mvn /usr/bin/mvn

# Kubernetes & Helm & eksctl
sudo curl -Lo /usr/local/bin/kubectl "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
sudo chmod +x /usr/local/bin/kubectl
sudo ln -sf /usr/local/bin/kubectl /usr/bin/kubectl

curl -s https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash
sudo ln -sf /usr/local/bin/helm /usr/bin/helm

curl --silent --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
sudo mv /tmp/eksctl /usr/local/bin
sudo ln -sf /usr/local/bin/eksctl /usr/bin/eksctl

echo "=========================================="
echo "Agent Setup Complete! Tool Versions:"
java -version 2>&1 | head -n 1
mvn -version | head -n 1
docker --version
kubectl version --client --short 2>/dev/null || kubectl version --client
helm version --short
eksctl version
echo "=========================================="
