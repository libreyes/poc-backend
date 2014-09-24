#/bin/sh
# NOTE: encounters.json can't be imported via the importer
mongoimport --db openeyes --collection patients --file patients.json
mongoimport --db openeyes --collection tickets --file tickets.json
mongoimport --db openeyes --collection workflows --file workflows.json
