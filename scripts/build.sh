#!/bin/bash
echo "Stopping all running docker containers"
docker stop $(docker ps -aq)
echo "Removing all docker containers"
docker rm $(docker ps -aq)
echo "Removing all docker volumes"
docker volume rm $(docker volume ls -q)
cd ../backend
echo "Booting docker-compose test file"
docker-compose --file src/test/resources/docker-compose.yml up -d
sleep 10
echo "Starting build and tests"
mvn package
echo "Stopping all running docker containers"
docker stop $(docker ps -aq)
cd ../scripts

