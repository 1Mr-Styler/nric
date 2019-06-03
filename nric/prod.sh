#!/usr/bin/env bash

git pull

git checkout d9dbdfe -- Dockerfile
git checkout d9dbdfe -- docker-compose.yml
git checkout d9dbdfe -- docker-entry.sh
sed -i -e 's/202/212/g' Dockerfile
sed -i -e 's/-pip/-pip libglib2.0-0/g' Dockerfile