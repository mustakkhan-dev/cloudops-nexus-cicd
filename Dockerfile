FROM eclipse-temurin:8-jre-alpine
ADD jarstaging/com/mustakkhan/demo-workshop/2.1.2/demo-workshop-2.1.2.jar sample_app.jar 
ENTRYPOINT [ "java", "-jar", "sample_app.jar" ]
