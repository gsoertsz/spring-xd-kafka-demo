# Spring XD clustered demo

## Introduction <a name="Introduction"></a>

This project is an all-in-one runtime setup for Spring-XD in distributed mode (with 2 XD-containers), with Rabbit-MQ used as the data-transport bus

## Overview <a name="Overview"></a>

This project is a full end-to-end application structure, for a message delivery service. It demonstrates several things:

* A message delivery service exposed as a ReST interface
* Spring XD as a message delivery fabric
* Integration with Kafka via:
    * Confluent.io Kafka Rest Proxy and Schema Registry via Avro 
    * In-stream Avro encoding and submission to Avro via XD Kafka sink
* Kafka consumer against both of the above message channels
* Avro encoding
* Multi container XD-cluster
* Key Confluent services:
    * Kafka Schema Registry
    * Kafka ReST Proxy
    
The entire application runs as an interconnected set of docker containers, described in the `docker-compose.yml` file.

Some images are built locally, some refer to 'publicly available' images (such as those published by confluent.io)

## Execution

### Introduction

The demo is bimodal in two ways, firstly in demonstrating: 

* Henceforth referred to as `kafka-via-xd-stream`, end-to-end message delivery via in stream avro encoding and kafka-sink into Kafka cluster, observable via downstream kafka-consumer
* Henceforth referred to as `kafka-via-confluent`, end-to-end message delivery via schema-id prefetch, and message text payload enrichment for an avro submit to the Confluent.io kafka ReST proxy, observable via downstream kafka-consumer

Secondly, it is necessary to build the images and required artefacts for the system, before bringing it up. Anecdotally, this involves:

* Running a local Artifactory instance, and building and 'publishing' a common message library shared by several components in the system.
* For each docker project, within and outside the `spring-xd` project, executing a docker build wrapper shell script, to populate your local docker environment
* In the case of `kafka-via-xd-stream` integration:
    * Provisioning a rabbit queue named `bet-processing`
    * Configuring an XD module (to perform in-stream avro encoding)
    * Configuring an XD stream, linking the bet-processing queue to the kafka topic via the avro encoding
* In the case of `kafka-via-confluent` integration, pre-priming the Confluent.io schema registry with a topic-specific avro schema

### Building the Demo

#### Spring XD Images

In the `spring-xd` project, execute the following command:

```
spring-xd:%> ./buildAllImages.sh 
```

This will build a base spring XD image, and component specific sub-images as required.

#### Running artifactory

```
anywhere %> docker run --name artifactory -d -p 8081:8081 docker.bintray.io/jfrog/artifactory-oss:latest
```

After the artifactory instance has started running, and has completed its initial setup, ensure to set the username and password to admin/admin, so the projects will authenticate successfully when retrieving their dependencies

#### Building the message library

Many of the projects in the system rely on a common messaging library in the `bdef-messaging` project. Build this project as follows:

```
bdef-messaging %> gradle clean build artifactoryPublish
```

#### Pull the Spotify Kafka image

You'll need a compact Kafka image and there's one here

```
anywhere %> docker pull spotify/kafka:latest 
```

#### Building the other images

From the parent directory of the `spring-xd` project directory, execute the following command:

```
parent %> for i in gateway-java-spring java-kafka-client-avro java-dispatcher; do
> pushd ./$i
> ./buildImage.sh
> popd
> done
```

#### Build the SpringXD processor module

From the `xd-be-pipeline` project directory, execute the following command:

```
xd-be-pipeline %> gradle clean build bootRepackage && cp build/libs/xd-be-pipeline-1.0-SNAPSHOT.jar ../spring-xd/
```

This will create the avro converter module archive which will be used later with spring xd to register the module in the platform, so that it can be used in the `kafka-via-xd-stream` stream configuration

### Starting the Demo Cluster

### Start the components

From the `spring-xd` project directory, execute the following command:

```
spring-xd %> docker-compose up -d
```

You may have to wait for the system to be up. Generally, the `gateway-api` container is one of the last containers to initialize, (aside from a few stragglers with no upstream dependencies). You should monitor it's logs with the following command:

```
spring-xd %> docker logs -f gateway-api
```

At this point the following containers will start:

* Gateway API
* Spring XD cluster including
    * Admin node
    * 2x Container nodes
    * Shared Redis node (single instance)
    * Shared Zookeeper
* Rabbit single node
* Dispatcher
* Kafka Server (including its own zookeeper)
* Confluent.io Kafka ReST proxy
* Confluent.io Schema Registry

#### Creating the `kafka-via-xd-stream` stream

##### Staring the XD shell

Streams need to be created via the XD-shell, which is available in the docker-compose environment via a shell container. After running the following command you will be presented with the XD-shell.

```
%> docker-compose run shell /bin/bash
springxd@c9a472c1054d:/opt/spring-xd-1.2.0.BUILD-SNAPSHOT%> shell/bin/xd-shell
```

Because of a limitation in spring-xd shell, you have to configure the shell's access to the admin server as part of the shell session as follows:

```
xd-shell %> admin config server http://admin:9393
```

... where 'admin' is the name of the container running the SpringXD admin node in the docker-compose environment

Create and deploy a 'stream' to read messages from the `bet-processing` queue, transform the message into a binary avro format, and then submit them to a kafka server using the topic name `test`

##### Uploading the built module jar

When started with the `docker-compose` command, the xd-shell container has the `spring-xd` directory bound as a volume mount, which means you have access to the `xd-be-pipeline-1.0-SNAPSHOT.jar` at container start up time.

This allows you to distribute the module via the shell and the admin node to all the containers. Execute the following command in the shell container:

```
xd-shell %> module upload --name bettoavro --type processor --file xd-be-pipeline-1.0-SNAPSHOT.jar --force
Successfully uploaded module 'processor:bettoavro'
```

##### Create the `bet-processing` queue

1. Log in to the rabbit admin interface at http://localhost:15672, using the credentials guest/guest
2. Navigate to 'Queues' using the tabbed menu
3. Create a queue called `bet-processing`

##### Creating a processing stream that pipes messages through the processor module

The shell command below configures a stream that takes messages from the inbound rabbit queue, converts them to avro and then sends them directly to the kafka server

```
xd-shell %> stream create --name bet-processing --definition "rabbit --addresses=rabbitmq --queues=bet-processing --username=guest --password=guest | bettoavro | kafka --brokerList=kafka:9092 --topic=test" --deploy
Created and deployed new stream 'bet-processing'
```

### Priming the schema registry for the `kafka-via-confluent` integration

Using a web-client (like _curl_, _wget_, or UI's like _Postman_ or _ARC_), POST a message to http://localhost:8082/subjects/test-proxy/versions with the following headers:

* Accept: application/vnd.schemaregistry.v1+json
* Content-Type: application/vnd.schemaregistry.v1+json

You should receive the following response:

```
Content-Length: 8
Content-Type: application/vnd.schemaregistry.v1+json
Date: Fri, 02 Jun 2017 04:31:04 GMT
Server: Jetty(9.2.12.v20150709)
Raw
JSON
 

{
  "id": 1
}
```

### Sending messages into Kafka

1. Open http://localhost:8100/v1/swagger-ui.html
2. Find the full sample bet message payload in `gateway-java-spring/data/examples/bet_example.json` and insert into the payload fields for both end-points:
    * /alternate/bet/{version}
        * For this api also insert '1' in the version field (so that version 1 schema you uploaded earlier is used to encode the message)
    * /bet
3. Click on `try it out!`, and observe the console logs of the `kafka-consumer-avro` container for how the messages are read


### Key ports/interfaces

To monitor and investigate the platform, see the interface ports below:

* Local Artifactory: http://localhost:8081/artifactory/webapp
* RabbitMQ management port: http://localhost:15672 (username: guest, password, guest)
* SpringXD admin interface: http://localhost:9393/admin-ui
* Message Delivery Service Gateway ReST Swagger UI: http://localhost:8100/v1/swagger-ui.html

## Configuration

To change the xd-cluster configuration, modify the `servers.yml` file, which is mounted into the admin and container nodes in the docker-compose file.

This is currently configured to override the xd-defaults (which use redis) for the data-bus.


## Built With

* [Docker](https://www.docker.com/community-edition) - platform independent execution and containerization

