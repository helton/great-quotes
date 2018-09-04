echo "Stopping all running docker containers"
docker stop $(docker ps -aq)

echo "Removing all docker containers"
docker rm $(docker ps -aq)

echo "Removing all docker volumes"
docker volume rm $(docker volume ls -q)

echo "Removing app node packages"
rm -rf ../../app/node_modules

echo "Removing api class files"
rm -rf ../../api/target