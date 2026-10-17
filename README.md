# 🚀 Implementation of the Entire Advanced CI/CD Pipeline with Major DevOps Tools

![DevOps Pipeline](https://imgur.com/WcCpKVU.png)

A comprehensive end-to-end CI/CD pipeline leveraging **Terraform**, **Ansible**, **Jenkins**, **SonarQube**, **JFrog**, **Docker**, **EKS**, **Prometheus**, and **Grafana**.

---

## 🧩 Pipeline Architecture Overview

> Below is a step-by-step implementation breakdown of the complete DevOps CI/CD process.

<details>
<summary><strong>🔧 Step 1: Infrastructure Provisioning (Terraform)</strong></summary>

- Provisioned **VPC**, **Security Groups**, **Ansible Controller**, **Jenkins Master**, and **Agent Instances** using Terraform.  
🔗 [Step Details](./Steps/step1.md)

</details>

<details>
<summary><strong>🔐 Step 2: SSH Configuration</strong></summary>

- Set up password-less authentication between Ansible Controller and Agent nodes.  
🔗 [Step Details](./Steps/step2.md)

</details>

<details>
<summary><strong>⚙️ Step 3: Jenkins Setup (Ansible)</strong></summary>

- Configured Jenkins Master and Agent nodes.  
- Agent configured as **Maven Build Server**.  
🔗 [Step Details](./Steps/step3.md)

</details>

<details>
<summary><strong>🔗 Step 4: Jenkins Master-Agent Integration</strong></summary>

- Connected Jenkins Master to Agent with credentials.  
🔗 [Step Details](./Steps/step4.md)

</details>

<details>
<summary><strong>🌐 Step 5: GitHub Integration</strong></summary>

- Added GitHub credentials.  
- Created **Multibranch Pipeline Job**.  
🔗 [Step Details](./Steps/step5.md)

</details>

<details>
<summary><strong>🚨 Step 6: Webhook Trigger Setup</strong></summary>

- Configured GitHub webhook trigger using **Multibranch Scan Webhook Trigger Plugin**.  
🔗 [Step Details](./Steps/step6.md)

</details>

<details>
<summary><strong>🧪 Step 7: SonarQube Integration</strong></summary>

- Generated access token in SonarCloud.  
- Installed SonarQube scanner plugin.  
- Added SonarQube server and scanner to Jenkins.  
- Configured `sonar-project.properties`.  
- Added **code quality**, **unit test**, and **build** stages in `Jenkinsfile`.  
🔗 [Step-by-step Setup](./Steps/step7.md)

</details>

<details>
<summary><strong>📦 Step 8: JFrog Artifactory Integration</strong></summary>

- Configured JFrog credentials.  
- Installed Artifactory plugin in Jenkins.  
🔗 [Details](./Steps/step8.md)

</details>

<details>
<summary><strong>🐳 Step 9: Docker Image & Push to JFrog</strong></summary>

- Built Docker image from `.jar`.  
- Pushed to JFrog Artifactory using **Docker Pipeline Plugin**.  
- Added **Docker Build & Publish** stage to Jenkinsfile.  
🔗 [Details](./Steps/step9.md)

</details>

<details>
<summary><strong>☸️ Step 10: EKS Cluster Setup</strong></summary>

- Created **EKS cluster** via Terraform.  
- Installed `kubectl` and AWS CLI in Jenkins slave.  
- Configured Kube credentials using:
  ```
  aws eks update-kubeconfig --region <region_name> --name <cluster_name>
  ```

🔗 [Details](./Steps/step10.md)

</details>

<details>
<summary><strong>🚀 Step 11: Kubernetes Deployment</strong></summary>

- Pulled Docker image using Kubernetes secrets.  
- Deployed it to EKS cluster using **Deployment** and **Service** resources.  
- Exposed via LoadBalancer.  
🔗 [Details](./Steps/step11.md)

</details>

<details>
<summary><strong>📊 Step 12: Monitoring (Prometheus & Grafana)</strong></summary>

- Added Prometheus Helm repo.  
- Enabled monitoring in EKS cluster.  
- Changed Prometheus and Grafana services to `LoadBalancer` for browser access.  
🔗 [Details](./Steps/step12.md)

</details>

---

## 👨‍💻 Author & Maintainer

This project is created and maintained by **[Mustak Khan](https://github.com/mustakkhan-dev)** 🚀

### 💬 Connect with Me:

- 🐙 **GitHub**: [@mustakkhan-dev](https://github.com/mustakkhan-dev)  
- 📧 **Email**: [mkhanmustak06@gmail.com](mailto:mkhanmustak06@gmail.com)  
- 👔 **LinkedIn**: [Mustak Khan](https://www.linkedin.com/in/mustakkhan66/)  

---
⭐ **Show Your Support:** If you found this project helpful, give it a star on GitHub!
