#!/usr/bin/env bash
# boostrap.sh - used by vagrant to bootsrap a dev environment

# Add python-software-properties
apt-get install python-software-properties -y

# Add MongoDB sources
apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 7F0CEB10
echo 'deb http://downloads-distro.mongodb.org/repo/ubuntu-upstart dist 10gen' | tee /etc/apt/sources.list.d/mongodb.list

# Add the Oracle Java PPA
add-apt-repository ppa:webupd8team/java -y

# Run apt-get update
apt-get update -y

# Install Mongo 2.6.1
apt-get install -y mongodb-org-server=2.6.1 mongodb-org-shell=2.6.1 mongodb-org-tools=2.6.1\

# Install Oracle Java 7 which best supports Java Advanced Imaging (JAI),
# or uncomment line below and comment out other 6 lines to install OpenJDK7 instead
# apt-get install -y openjdk-7-jdk
su -c "echo debconf shared/accepted-oracle-license-v1-1 select true | sudo debconf-set-selections && echo debconf shared/accepted-oracle-license-v1-1 seen true | sudo debconf-set-selections"
apt-get install oracle-java7-installer -y

# Install ImageMagick
apt-get install -y imagemagick

# Run the import script
# TODO: Should this just be done when the user wants to and not part of the vagrant script?
# su -c "cd /app/docs/sample && ./import.sh" vagrant

# TODO: I think these need to be used...
# LD_LIBRARY_PATH=/app/lib/linux-x86_64/clib_jiio
# export LD_LIBRARY_PATH

# Run sbt to install all the jars
# TODO: Figure out how to start sbt with the container and auto compile.
# su -c "cd /app && ./sbt container:start" vagrant