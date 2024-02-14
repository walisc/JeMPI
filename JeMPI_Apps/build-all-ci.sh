#!/bin/bash

set -e
set -u

pushd JeMPI_Configuration
  ./create.sh reference/config-reference.json 
popd

cp -L -f ./JeMPI_Configuration/config-api.json ./JeMPI_API/src/main/resources/config-api.json
cp -L -f ./JeMPI_Configuration/config-api.json ./JeMPI_API_KC/src/main/resources/config-api.json

mvn clean package
pushd JeMPI_EM_Scala
  sbt clean assembly
popd

pushd JeMPI_AsyncReceiver
  ./build.sh || exit 1
popd
pushd JeMPI_ETL
  ./build.sh || exit 1
popd
pushd JeMPI_Controller
  ./build.sh || exit 1
popd
pushd JeMPI_EM_Scala
  ./build.sh || exit 1
popd
pushd JeMPI_Linker
  ./build.sh || exit 1
popd
pushd JeMPI_API
  ./build.sh || exit 1
popd
pushd JeMPI_API_KC
  ./build.sh || exit 1
popd
pushd JeMPI_Bootstrapper
  ./build.sh || exit 1
popd
pushd JeMPI_UI
  ./build-image.sh || exit 1
popd