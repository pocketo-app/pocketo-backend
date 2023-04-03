#!/bin/bash

# http://redsymbol.net/articles/unofficial-bash-strict-mode/
set -e
set -u

# https://www.shellcheck.net/
# Unavailable in POSIX sh
set -o pipefail
IFS=$'\n\t'

export APP_IMAGE=$1 # Let all commands below use this env var
COMMAND=$2
ENV_FILE=./pocketo-config/.env # Must use relative path
BLUE_SERVICE=app-blue
GREEN_SERVICE=app-green
WAIT_TIME=30

if [[ $COMMAND == "start" ]]
then
	echo Start all services
	docker compose --env-file $ENV_FILE up -d
	echo Done
	exit 0
elif [[ $COMMAND == "stop" ]]
then
	echo Stop all services
	docker compose --env-file $ENV_FILE down
	echo Done
	exit 0
elif [[ $COMMAND == "restart" ]]
then
	echo Start pulling the Docker image
	docker image pull "$APP_IMAGE" # Always pull the latest version of the specific tag

	# Can not use restart command, must stop and up again
  # https://docs.docker.com/engine/reference/commandline/compose_restart/
	echo Stop the green service
	docker compose --env-file $ENV_FILE stop $GREEN_SERVICE
	echo Restart the green service
	docker compose --env-file $ENV_FILE up $GREEN_SERVICE -d
	echo "Wait $WAIT_TIME seconds for the green service starting"
	sleep $WAIT_TIME

	echo Stop the blue service
	docker compose --env-file $ENV_FILE stop $BLUE_SERVICE
	echo Restart the blue service
	docker compose --env-file $ENV_FILE up $BLUE_SERVICE -d
	echo "Wait $WAIT_TIME seconds for the blue service starting"
	sleep $WAIT_TIME

	echo Done
	exit 0
fi

echo Invalid command
exit 1
