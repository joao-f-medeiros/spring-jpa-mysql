## Use to run mysql db docker image, optional if you are not using a local mysqldb
# docker run --name mysql -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -v {localVolume}:/var/lib/mysql -p 3306:3306 -d mysql

# connect to mysql and run as root user
# docker exec -it {imageName} bash
# mysql -u root
#Create Databases
CREATE DATABASE springjpa_dev;
CREATE DATABASE springjpa_prod;

#Create database service accounts
CREATE USER 'springjpa_dev_user'@'localhost' IDENTIFIED BY 'root';
CREATE USER 'springjpa_prod_user'@'localhost' IDENTIFIED BY 'root';
CREATE USER 'springjpa_dev_user'@'%' IDENTIFIED BY 'root';
CREATE USER 'springjpa_prod_user'@'%' IDENTIFIED BY 'root';

#Database grants
GRANT ALL ON springjpa_dev.* TO 'springjpa_dev_user'@'localhost';
GRANT ALL ON springjpa_prod.* TO 'springjpa_prod_user'@'localhost';
GRANT ALL ON springjpa_dev.* TO 'springjpa_dev_user'@'%';
GRANT ALL ON springjpa_prod.* TO 'springjpa_prod_user'@'%';