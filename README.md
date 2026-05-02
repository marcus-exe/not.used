# Apache Tomcat Server Docker Application

## How the project is structured?
  
Dockerfile <br>
pom.xml <br>
.gitignore <br>
README.md <br>
run.sh <br>
src/main/webapp/\<jsp files>.jsp <br>
src/main/java/com/example/servlet/\<servlet files>.java




## How to delete old images
```
docker rm $(docker ps -a -q) -f 
```
## How to run the project (step by step)?

### 1. Build the java code
```
mvn clean package
```
### 2. Build the docker image
```
docker build -t localhost/my-jsp-app:latest .
```
### 3. Run the project:
```
docker run -d -p 8080:8080 localhost/my-jsp-app:latest
```
You'll find the application running at
http://localhost:8080/index.jsp
...

## How to run the project?
### Give execution permission to `run.sh`
```
chmod +x run.sh
```
### Run the shell script
```
./run.sh
```
# agnello-fiap-delivery
