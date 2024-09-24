#!/bin/bash

declare dc_db=docker-compose.yml
declare dc_app=docker-compose-app.yml

#script built to speed up docker composing by shortening commands

function build_api() {
  cd movie-test/
  mvn spring-boot:build-image -Dspring-boot.build-image.imageName=musimarc/movie-api
  cd ..
}

function start_dc() {
  echo "Starting docker compose"
  docker-compose -f ${dc_db} up -d
  docker-compose -f ${dc_app} logs -f
}

function stop_dc() {
  echo "Stopping docker compose and removing app compose"
  docker-compose -f ${dc_db} stop
  docker-compose -f ${dc_app} rm -f
}

function start() {
  build_api
  echo "Starting containers"
  docker-compose -f ${dc_db} -f ${dc_app} up --build -d
  docker-compose -f ${dc_db} -f ${dc_app} logs -f
}

function stop() {
  echo "Stopping containers"
  docker-compose -f ${dc_db} -f ${dc_app} stop
  docker-compose -f ${dc_db} -f ${dc_app} rm -f
}

function restart() {
  stop
  start
}

action="start"

#if there is argument assign to action that argument
if [ "$#" != "0" ]
then
  action=$@
fi

eval ${action}