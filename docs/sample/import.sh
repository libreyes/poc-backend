#/bin/sh
mongo openeyes --eval "db.dropDatabase();"
mongoimport --db openeyes --collection encounters --file encounters.json
mongoimport --db openeyes --collection patients --file patients.json
mongoimport --db openeyes --collection tickets --file tickets.json
mongoimport --db openeyes --collection workflows --file workflows.json
