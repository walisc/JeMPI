#!/bin/bash

cd "$1"

for dir in */; do
  dir="${dir%/}" 
  if [ -f "$dir/docker" ] && [ -f "$dir/build.sh" ]; then
    echo "Building docker images for $dir (tag: $3)"
    ./$dir/build.sh
  fi
done
