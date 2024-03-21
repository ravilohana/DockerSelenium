FROM bellsoft/liberica-openjdk-alpine:17.0.8


# install curl jq

RUN apk add curl jq

# workspace/word directory

WORKDIR /home/selenium-docker

# Add the required files

ADD target/docker-resources ./
ADD https://raw.githubusercontent.com/ravilohana/DockerSelenium/master/runner.sh    runner.sh

# Run the tests
ENTRYPOINT sh runner.sh
