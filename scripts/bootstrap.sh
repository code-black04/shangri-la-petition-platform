mvn clean
mvn install -DskipTests
cd docker/tomcat/ || exit
rm -R shangri-la-petition-platform.jar
cd ../../
cp target/shangri-la-petition-platform.jar docker/tomcat/
cd docker/postgres || exit
echo $PWD
docker build -t shangri-la-petition-platform-image -f Dockerfile .
cd ../../
echo $PWD
cd docker/tomcat || exit
docker build -t shangri-la-petition-platform-image -f Dockerfile .
cd ../../
echo $PWD
cd docker/app || exit
docker build -t shangrila-petition-platform-image -f Dockerfile .