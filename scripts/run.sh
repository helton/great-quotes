#!/bin/bash
echo "Booting docker-compose production file"
docker-compose --file ../docker/docker-compose.yml up --build
