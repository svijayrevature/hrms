#!/bin/bash

#Constants

REGION=$1
PROJECT_NAME=$2
DOCKER_TAG=$3
DOCKER_REGISTRY_URL=$4
AWS_CRED_PATH=$5
AWS_CONFIG_PATH=$6
CLUSTER=$7
SERVICE=$8
TASK_DEFINITION=$9

cd ${PROJECT_NAME}


#Gradle Build Commands
echo "<<<<<<<<<<Initiating Build>>>>>>>>>>"
./gradlew clean
./gradlew bootJar -x test
echo "<<<<<<<<<<BUILD COMPLETE>>>>>>>>>>"

#Docker Login
echo "<<<<<<<<<<LOGGING INTO DOCKER FOR ACCESSING REPOSITORY>>>>>>>>>>"
DOCKER_LOGIN="$(AWS_SHARED_CREDENTIALS_FILE=${AWS_CRED_PATH} AWS_CONFIG_FILE=${AWS_CONFIG_PATH} aws ecr get-login --no-include-email --region ${REGION} )"
${DOCKER_LOGIN}
echo "<<<<<<<<<<LOGGED IN>>>>>>>>>>"

#Docker Build Commands

echo "<<<<<<<<<<BUILDING DOCKER IMAGE>>>>>>>>>>"
docker build -t ${PROJECT_NAME} .
echo "<<<<<<<<<<COMPLETED IMAGING THE ${PROJECT_NAME} application>>>>>>>>>>"
echo "<<<<<<<<<<TAGGING THE IMAGE TO MARK THE RESPECTIVE BUILD>>>>>>>>>>"
docker tag ${PROJECT_NAME}:latest ${DOCKER_REGISTRY_URL}/${PROJECT_NAME}:${DOCKER_TAG}
echo "<<<<<<<<<<PUSHING IMAGE TAGGED ${DOCKER_TAG} TO REMOTE REPPO>>>>>>>>>>"
docker push ${DOCKER_REGISTRY_URL}/${PROJECT_NAME}:${DOCKER_TAG}
echo "<<<<<<<<<<PUSH SUCCEEDED>>>>>>>>>>"

#AWS service update command
echo "<<<<<<<<<<UPDATING THE AWS ECS PREDEFINED SERVICE>>>>>>>>>>"
AWS_SHARED_CREDENTIALS_FILE=${AWS_CRED_PATH} AWS_CONFIG_FILE=${AWS_CONFIG_PATH} aws ecs update-service --cluster ${CLUSTER} --service ${SERVICE} --task-definition ${TASK_DEFINITION} --force-new-deployment
echo "<<<<<<<<<<RESTARTED THE ECS CLUSTER SUCCESSFULLY>>>>>>>>>>"
