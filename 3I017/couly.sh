##COULY LAUNCHER##

#Création de l'app#
cd /tmp
create-react-app couly2
cd couly2

#Installation package#
npm install bootstrap@3
npm install jquery@1.12.4
npm install local-storage
npm install react-helmet
npm install axios

#Copie des sources#
rm -rf src
cp -r ~/3I017/src src

#Source SQL
mysql -u root -proot -e "	CREATE DATABASE IF NOT EXISTS 3i017"
mysql -u root -proot -e "source ~/3I017/3i017.sql" 3i017
#mysql -u root -proot 3i017 < ~/3I017/3i017.sql

#Source Mongo
java -jar MongoIO.jar -source 3i017_mongo comments MONGOBASE.json

npm start
