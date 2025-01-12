cd docker/postgres || exit
docker stop shangri-la-petition-platform-postgres-container
docker rm shangri-la-petition-platform-postgres-container
docker images
docker rmi -f shangri-la-petition-platform-postgres-image
cd ../..
cd docker/tomcat || exit
docker stop shangri-la-petition-platform-container
docker rm shangri-la-petition-platform-container
docker images
docker rmi -f shangri-la-petition-platform-image