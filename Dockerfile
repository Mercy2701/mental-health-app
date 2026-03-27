FROM eclipse-temurin:17

# Install Python
RUN apt-get update && apt-get install -y python3 python3-pip

# Optional: alias python
RUN ln -s /usr/bin/python3 /usr/bin/python

# Set working directory
WORKDIR /app

# Copy project
COPY . .

# Install Python dependencies
RUN pip3 install -r requirements.txt

# Build Spring Boot
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 8080

# Run app
CMD ["java","-jar","target/mental_health2-0.0.1-SNAPSHOT.jar"]jar"]