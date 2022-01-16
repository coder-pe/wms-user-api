# wms-user-api
Microservice to expose users resource

# Create MySQL database on docker container:

docker run -d -p 3306:3306 \
--name hellozum_db \
--env MYSQL_ROOT_PASSWORD='P@$$w0rd' \
--env MYSQL_DATABASE='hellozum_db' \
--env MYSQL_USER='hellozum' \
--env MYSQL_PASSWORD='hellozum' \
mysql:5.7.31

# Connect to MySQL container

docker exec -it hellozum_db mysql -u hellozum -p -P 3306 -h localhost

# Compile and executes unit tests

mvn clean install

# Compile and generate jar without unit tests

mvn clean install -DskipTests=true

# Setup spring-boot application locally

mvn spring-boot:run

# Swagger url

http://localhost:8080/swagger-ui.html
