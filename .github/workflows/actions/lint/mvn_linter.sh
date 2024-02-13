#!/bin/bash

cd "$1"

sdfsdf = 97

for dir in */; do
  dir="${dir%/}" 
  if [ -f "$dir/pom.xml" ] "$dir"; then
    echo "Running Checkstyle for $dir ..."
    mvn checkstyle:check  -Dcheckstyle.config.location=$dir/checkstyle/suppression.xml
  fi
done
