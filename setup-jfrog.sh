#!/bin/bash
set -e
echo "=========================================="
echo "Starting JFrog Artifactory Automated Setup..."
echo "=========================================="
sudo yum update -y
sudo yum install -y docker
sudo systemctl enable --now docker
sudo usermod -aG docker ec2-user

# Setup 2GB swap space to prevent low-memory crashes
if [ ! -f /swapfile ]; then
    echo "Creating 2GB swap space..."
    sudo dd if=/dev/zero of=/swapfile bs=128M count=16
    sudo chmod 600 /swapfile
    sudo mkswap /swapfile
    sudo swapon /swapfile
    echo '/swapfile swap swap defaults 0 0' | sudo tee -a /etc/fstab
fi

# Run Artifactory container
sudo docker rm -f artifactory 2>/dev/null || true
sudo docker run -d --name artifactory --restart always -p 8081:8081 -p 8082:8082 releases-docker.jfrog.io/jfrog/artifactory-oss:7.77.5

echo "=========================================="
echo "JFrog Artifactory container started!"
echo "Wait ~45 seconds, then open in browser:"
echo "Port: 8082 | User: admin | Password: password"
echo "=========================================="
