Write-Output "Stopping all running docker containers"
docker stop $(docker ps -aq)

Write-Output "Removing all docker containers"
docker rm $(docker ps -aq)

Write-Output "Removing all docker volumes"
docker volume rm $(docker volume ls -q)

Write-Output "Removing app node packages"
Remove-Item ..\..\app\node_modules -Recurse -ErrorAction Ignore

Write-Output "Removing api class files"
Remove-Item ..\..\api\target -Recurse -ErrorAction Ignore