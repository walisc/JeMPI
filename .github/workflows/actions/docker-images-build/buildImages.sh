#!/bin/bash

source $GITHUB_WORKSPACE/devops/linux/docker/conf.env

cd "$1"

for dir in */; do
  dir="${dir%/}" 
  if [ "$(basename "$dir")" = "JeMPI_UI" ]; then
    echo "Building docker images for ui: $dir (tag: $2)"
    pushd $dir
      ./build-image.sh || exit 1
    popd
  elif [ -d "$dir/docker" ] && [ -f "$dir/build.sh" ]; then
    echo "Building docker images for $dir (tag: $2)"
    pushd $dir
      ./build.sh || exit 1
    popd
  fi
done
