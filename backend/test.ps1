echo "Stopping all running docker containers"
docker stop $(docker ps -aq)
echo "Removing all docker containers"
docker rm $(docker ps -aq)
echo "Removing all docker volumes"
docker volume rm $(docker volume ls -q)
echo "Booting docker-compose test file"
docker-compose --file ..\docker\docker-compose.yml up -d
sleep 10
echo "Starting build and tests"
mvn clean test
echo "Stopping all running docker containers"
docker stop $(docker ps -aq)