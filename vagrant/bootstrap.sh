#!/usr/bin/env bash
# boostrap.sh - used by vagrant to bootsrap a dev environment

# Add apt sources
apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 7F0CEB10
echo 'deb http://downloads-distro.mongodb.org/repo/ubuntu-upstart dist 10gen' | tee /etc/apt/sources.list.d/mongodb.list
apt-get update

# Install dependencies
apt-get install -y \
	openjdk-7-jdk \
	mongodb-org-server=2.6.1 mongodb-org-shell=2.6.1 mongodb-org-tools=2.6.1\
	imagemagick

su -c "cd /app/docs/sample && ./import.sh" vagrant
su -c "cd /app && ./sbt update" vagrant