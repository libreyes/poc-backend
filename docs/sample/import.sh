#/bin/sh
#
echo "Dropping existing Mongo database"
mongo openeyes --eval "db.dropDatabase();"

encounters=encounters/*

for encounter in $encounters
do
  echo "Importing encounters for $encounter"
  mongoimport --db openeyes --collection encounters --file $encounter
done

echo "Importing patients"
mongoimport --db openeyes --collection patients --file patients.json

echo "Importing tickets"
mongoimport --db openeyes --collection tickets --file tickets.json

echo "Importing workflows"
mongoimport --db openeyes --collection workflows --file workflows.json
