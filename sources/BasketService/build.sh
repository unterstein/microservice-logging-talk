#!/bin/bash

cd $(dirname $0)

mvn clean install
docker build -t basketservice:latest .
