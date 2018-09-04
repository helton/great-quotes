#!/bin/sh

cd ..

cd ../api
echo "Booting docker-compose test file"
docker-compose --file src/test/resources/docker-compose.yml up -d

echo "Starting build api"
# mvn clean package -Dmaven.test.skip=true
docker run -it --rm --name great-quotes-api -v $(PWD):/usr/src/app -w /usr/src/app maven:3.5.4-jdk-8 mvn clean package -Dmaven.test.skip=true

echo "Starting build app"
cd ../app
# npm install
docker run -it --rm --name great-quotes-app -v $(PWD):/usr/src/app -w /usr/src/app node:10 npm install

echo "Stopping all running docker containers"
docker stop $(docker ps -aq)

echo "Building images"
cd ../docker
docker-compose build

cd ../scripts/linux-mac