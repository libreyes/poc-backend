#!/usr/bin/env bash
# boostrap.sh - used by vagrant to bootsrap a dev environment

# Install Mongo 2.6.1
apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 7F0CEB10
echo 'deb http://downloads-distro.mongodb.org/repo/ubuntu-upstart dist 10gen' | tee /etc/apt/sources.list.d/mongodb.list
apt-get update -y
apt-get install -y mongodb-org-server=2.6.1 mongodb-org-shell=2.6.1 mongodb-org-tools=2.6.1\

# Install Oracle Java 7 which best supports Java Advanced Imaging (JAI),
# or uncomment line below and comment out other 6 lines to install OpenJDK7 instead
apt-get install -y openjdk-7-jdk
#apt-get install python-software-properties -y
#add-apt-repository ppa:webupd8team/java -y
#apt-get update -y
#debconf shared/accepted-oracle-license-v1-1 select true | sudo debconf-set-selections
#debconf shared/accepted-oracle-license-v1-1 seen true | sudo debconf-set-selections
#apt-get install oracle-java7-installer -y

# Install ImageMagick
apt-get install -y imagemagick

# Run the import script
su -c "cd /app/docs/sample && ./import.sh" vagrant
su -c "cd /app && ./sbt update" vagrant