#!/bin/bash

script_directory=$(dirname "$0")
images_path="$script_directory/docker-images"

if [ ! -d "$images_path" ]; then
    mkdir -p "$images_path"
fi

IMAGE_LIST=$(docker image ls --filter "reference=*:$1" --format "{{.Repository}}:{{.Tag}}")

for IMAGE in $IMAGE_LIST; do
    echo "Saving image: $IMAGE"
    IFS=':' read -a image_details <<< "$IMAGE"
    docker save -o "$images_path/${image_details[0]}.${image_details[1]}.tar" "$IMAGE"
done