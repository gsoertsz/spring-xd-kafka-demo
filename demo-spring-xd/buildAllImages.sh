#!/bin/bash 

# build the base image first
pushd ./image-base/latest
docker build -t springxd/base .
popd

for i in admin container hsqldb zookeeper shell singlenode; do
	pushd ./image-$i/latest
	docker build -t springxd/$i .
	popd
done
