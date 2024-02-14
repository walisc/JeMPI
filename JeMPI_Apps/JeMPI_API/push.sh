#!/bin/bash

set -e
set -u

source $PROJECT_DEVOPS_DIR/conf.env
source $PROJECT_DEVOPS_DIR/conf/images/conf-app-images.sh

docker tag ${API_IMAGE} ${REGISTRY_NODE_IP}/${API_IMAGE}
docker push ${REGISTRY_NODE_IP}/${API_IMAGE}
docker rmi ${REGISTRY_NODE_IP}/${API_IMAGE}
 
