Set-Location ..

Set-Location ..\api
Write-Output "Booting docker-compose test file"
docker-compose --file .\src\test\resources\docker-compose.yml up -d

Write-Output "Starting build and tests"
# mvn clean package '-Dmaven.test.skip=true'
docker run -it --rm --name great-quotes-api -v ${PWD}:/usr/src/app -v $env:USERPROFILE\.m2:/root/.m2 -w /usr/src/app maven:3.5.4-jdk-8 mvn clean package '-Dmaven.test.skip=true'

Write-Output "Starting build app"
Set-Location ..\app
# npm install
docker run -it --rm --name great-quotes-app -v ${PWD}:/usr/src/app -w /usr/src/app node:10 npm install

Write-Output "Stopping all running docker containers"
docker stop $(docker ps -aq)

Write-Output "Building images"
Set-Location ..\docker
docker-compose build

Set-Location ..\scripts\windows