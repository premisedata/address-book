#!/bin/bash

docker run \
    --name address-book-mysql \
    --env MYSQL_ROOT_PASSWORD=terrible-security \
    --publish 3306:3306 \
    --detach \
    mysql:latest
