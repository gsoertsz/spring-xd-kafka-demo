#!/bin/bash

gradle clean distTar && docker build -t kafka-consumer-avro .
