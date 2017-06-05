#!/bin/bash

gradle clean distTar && docker build -t java-dispatcher .
