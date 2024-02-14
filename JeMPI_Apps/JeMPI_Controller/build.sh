#!/bin/bash

set -e
set -u

source $PROJECT_DEVOPS_DIR/conf/images/conf-app-images.sh
source ../build-check-jdk.sh

JAR_FILE=${CONTROLLER_JAR}
APP_IMAGE=${CONTROLLER_IMAGE}
APP=controller
 
source ../build-app-image.sh
