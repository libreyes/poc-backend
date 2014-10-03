#!/usr/bin/env bash
# boostrap.sh - used by vagrant to bootsrap a dev environment

# Add apt sources
apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 7F0CEB10
echo 'deb http://downloads-distro.mongodb.org/repo/ubuntu-upstart dist 10gen' | tee /etc/apt/sources.list.d/mongodb.list
apt-get update

# Install Java & Mongo
apt-get install -y \
  openjdk-7-jdk \
  mongodb-org-server=2.6.1 mongodb-org-shell=2.6.1 mongodb-org-tools=2.6.1

# Run as user vagrant from here
su - vagrant

# Import sample data
cd /app/docs/sample && ./import.sh

# Start the api service
# cd /app && ./sbt ~container:start &