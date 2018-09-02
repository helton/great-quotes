cd ..
echo "Stopping all running docker containers"
docker stop $(docker ps -aq)
echo "Removing all docker containers"
docker rm $(docker ps -aq)
echo "Removing all docker volumes"
docker volume rm $(docker volume ls -q)
cd ..\backend
echo "Booting docker-compose test file"
docker-compose --file ..\src\test\resources\docker-compose.yml up -d
echo "Starting build and tests"
mvn clean package -Dmaven.test.skip=true
echo "Starting build frontend"
cd ..\frontend
npm install
echo "Stopping all running docker containers"
docker stop $(docker ps -aq)
echo "Building images"
cd ..\docker
docker-compose build