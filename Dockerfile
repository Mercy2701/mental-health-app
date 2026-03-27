FROM eclipse-temurin:17

WORKDIR /app

COPY . .

RUN apt-get update && apt-get install -y \
    python3 \
    python3-pip \
    build-essential \
    python3-dev \
    maven

RUN pip3 install --break-system-packages --no-cache-dir -r requirements.txt

RUN mvn clean package -DskipTests

EXPOSE 8080

CMD ["java","-jar","target/mental_health2-0.0.1-SNAPSHOT.jar"]
