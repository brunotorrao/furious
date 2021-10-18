docker-compose rm
docker-compose down

echo cleaning build
rm -rf build

./gradlew bootJar

API_KEY=$1 docker-compose up

