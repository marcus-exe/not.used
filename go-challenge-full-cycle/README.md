## Hello World in GO inside a Docker file

1. Initialize the Module 
```
go mod init containerized-go-app
```
2. Create a main file
```
touch main.go
```
3. Create a Dockerfile and run the following commands:
```
docker build . -t user_name/go-containerized:latest
docker run go-containerized:latest
```
imagem Docker: https://hub.docker.com/repository/docker/marcus21/go-containerized/general
