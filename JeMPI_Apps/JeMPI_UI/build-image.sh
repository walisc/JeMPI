#!/bin/bash

set -e
set -u

source $PROJECT_DEVOPS_DIR/conf.env
source $PROJECT_DEVOPS_DIR/conf/images/conf-app-images.sh

rm -f ./.env

envsubst < $PROJECT_DEVOPS_DIR/conf/ui/.env > ./.env

[ -z $(docker images -q ${UI_IMAGE}) ] || docker rmi ${UI_IMAGE}
docker system prune --volumes -f
# Injects env vars in .env as build args for use when the UI is built
docker build --tag "$UI_IMAGE" $(cat .env | sed '/^ *$/d' | sed 's@^@--build-arg @g' | paste -s -d " ") --target production-stage .
