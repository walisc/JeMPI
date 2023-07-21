# JeMPI

![Untitled design](https://user-images.githubusercontent.com/41700488/158391814-b78219dc-0359-4024-b7bd-2dec792b5b15.png)

The Jembi MPI, also known as JeMPI, is a standards-based client registry (CR) or master patient index (MPI). JeMPI facilitates the exchange of patient information between different systems and holds patient identifiers that may include patient demographic information. This is a necessary tool for public health to help manage patients, monitor outcomes, and conduct case-based surveillance. JeMPI’s primary goal is to act as a tool in order to solve the issue of multiple or duplicated patient records that are submitted from multiple point of service systems such as electronic medical records, lab systems, radiology systems and other health information systems. This is achieved by matching the various patient records from different systems under a Master Patient record with a unique ID. This allows for downstream applications, such as surveillance, to accurately display data and information on patient records without the worry that the data contains multiple records for the same patient.

## Requirements
- linux (bash >= 4.x)
  - docker (non-root)
  - maven
  - sbt
  - java 17.0.6
  - python 3.7
    - wxpython
    - requests

## Installation
- Directory structure
  - \<base>
    - JeMPI           - ```git@github.com:jembi/JeMPI.git```
    - JeMPI_TestData  - ```git@github.com:jembi/JeMPI_TestData.git```
- Requirements
  - ```ping `hostname` ``` must ping a LAN IP address (not 127.x.x.x) 
### Docker
- Run
  1. **_\<base>/JeMPI/docker/conf/env_**
     1. if you have less than 32Gb of ram, run ```./create-env-linux-low-1-.sh```. If you have 32Gb of ram or more, run ```./create-env-linux-high-1-.sh``` 
  2. **_\<base>/JeMPI/docker/helper/scripts_**
     1. ```bash ./x-swarm-a-set-insecure-registries.sh```
        - this clobbers **_/etc/docker/daemon.json_**   
  3. **_\<base>/JeMPI/docker_**
     1. ```./a-images-1-pull-from-hub.sh```
     2. ```./b-swarm-1-init-node1.sh```
     3. ```./c-registry-1-create.sh```
     4. ```./c-registry-2-push-hub-images.sh```
     5. ```./z-stack-3-build-reboot.sh```
### Local
#### Requirements
1. Install Postgresql
   1. sudo apt update 
   2. sudo apt install postgresql postgresql-contrib

2. Install DGraph
   1. curl https://get.dgraph.io -sSf | sudo bash
   2. dgraph version
   3. create system account
      1. sudo groupadd --system dgraph
      2. sudo useradd --system -d /var/run/dgraph -s /bin/false -g dgraph dgraph
   4. create directories
      1. sudo mkdir -p /var/log/dgraph
      2. sudo mkdir -p /var/run/dgraph/{p,w,zw}
      3. sudo chown -R dgraph:dgraph /var/{run,log}/dgraph
   5. sudo cp native/data-dgraph/dgraph-alpha.service /etc/systemd/system/
   6. enable services
      1. sudo systemctl daemon-reload
      2. sudo systemctl enable --now dgraph-alpha
      3. sudo systemctl enable --now dgraph-zero
3. Start Docker Apps
    1. **_\<base>/JeMPI/native/conf/env_**
        1. if you have less than 32Gb of ram, run ```./create-env-linux-low-1-.sh```. If you have 32Gb of ram or more, run ```./create-env-linux-high-1-.sh```
    2. **_\<base>/JeMPI/native/helper/scripts_**
        1. ```bash ./x-swarm-a-set-insecure-registries.sh```
            - this clobbers **_/etc/docker/daemon.json_**
    3. **_\<base>/JeMPI/native_**
        1. ```./a-images-1-pull-from-hub.sh```
        2. ```./b-swarm-1-init-node1.sh```
        3. ```./c-registry-1-create.sh```
        4. ```./c-registry-2-push-hub-images.sh```
        5. ```./z-stack-3-build-reboot.sh```
4. Start Java Apps
   1.  **_\<base>/JeMPI/native_**
       1. ```./helper/java/start-all-java-apps.sh```
   

## Development
It's possible to run the whole stack local without having to use a local registry using the command : 
```
./launch-local.sh
```
In order to remove the stack, you could use the following command :
```
docker stack remove jempi
```

## Support for Mac OS

For MacOS users, the `envsubst` command will fail, so you'll need to add it  :
```
brew install gettext
brew link --force gettext 
```

Other bash commands may fail (such as "declare -A"). So you may want to update bash >=v4:
```
brew install bash
bash --version
``` 

Then to run the stack locally, you will need to switch to bash first :
```
bash ./launch-local.sh
```

Note that currently, there is no Dgraph Ratel docker image compiled for M1 CPU, so most certainly you would run into a "Unsupported platform" error. For this you could either decide not to run the Ratel service by setting `export SCALE_RATEL=0` in `docker/conf/env/create-env-linux-1.sh` and use [https://play.dgraph.io/](https://play.dgraph.io/) instead.

