# get the smaller go-version
FROM golang:alpine as builder

# create a dir called /app inside the container and set as working directory
WORKDIR /app

# copy the created files to the container
COPY go.mod .
COPY main.go .

# build the binary bin inside /app
RUN CGO_ENABLED=0 GOOS=linux go build -ldflags="-s -w" -o bin . 

# Use a minimal base image for the final image
FROM scratch

# Copy the compiled binary from the builder stage
COPY --from=builder /app/bin /bin

# run this file
ENTRYPOINT [ "/bin" ]