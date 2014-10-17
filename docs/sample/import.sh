#!/bin/sh

database="openeyes"

while getopts ":d:" opt; do
  case $opt in
    d)
      echo "-d was triggered, Parameter: $OPTARG" >&2
      database=$OPTARG
      ;;
    \?)
      echo "Invalid option: -$OPTARG" >&2
      exit 1
      ;;
    :)
      echo "Option -$OPTARG requires an argument." >&2
      exit 1
      ;;
  esac
done

echo "Dropping existing Mongo database"
mongo $database --eval "db.dropDatabase();"

# encounters=encounters/*
# for encounter in $encounters
# do
#   echo "Importing encounters for $encounter"
#   mongoimport --db $database --collection encounters --file $encounter
# done

echo "Importing encounters"
mongoimport --db $database --collection encounters --file encounters.json

echo "Importing patients"
mongoimport --db $database --collection patients --file patients.json

echo "Importing tickets"
mongoimport --db $database --collection tickets --file tickets.json

echo "Importing workflows"
mongoimport --db $database --collection workflows --file workflows.json
