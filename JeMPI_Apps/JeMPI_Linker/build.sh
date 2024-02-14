#!/bin/bash

set -e
set -u

source $PROJECT_DEVOPS_DIR/conf/images/conf-app-images.sh
source ../build-check-jdk.sh

JAR_FILE=${LINKER_JAR}
APP_IMAGE=${LINKER_IMAGE}
APP=linker
 
source ../build-app-image.sh
