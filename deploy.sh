#!/bin/bash
##
## Usage:
## ./deploy.sh $version
## lxkssg.520

version=$1

PROJECT_NAME=demo

SERVER_HOST=106.14.72.68

SERVER_FOLDER=/usr/src

SERVER_USER=root

ZIP_NAME=$PROJECT_NAME.zip



echo '=== maven package start ==='
mvn clean package -DskipTests=true
if [[ $? -eq 0 ]]; then
  echo '=== maven package finished ==='
else
  echo 'maven package failed, deploy terminated!!!'
  exit 2
fi

echo '=== zip package start ==='
rm $ZIP_NAME
zip -r -q $ZIP_NAME target/$PROJECT_NAME-$version.jar
echo '=== zip package finished ==='

echo '=== transfer to remote server start ==='
scp $ZIP_NAME $SERVER_USER@$SERVER_HOST:$SERVER_FOLDER
echo '=== transfer to remote server finished ==='

echo '=== executing script on remote server start ==='
ssh $SERVER_USER@$SERVER_HOST /bin/bash << remotessh
cd $SERVER_FOLDER
unzip -oq $ZIP_NAME
exit
remotessh
echo '=== executing script on remote server finished ==='

echo '=== deploy finished ==='
