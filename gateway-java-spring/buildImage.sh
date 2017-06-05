#!/bin/bash

gradle clean distTar && docker build -t gateway-api .
