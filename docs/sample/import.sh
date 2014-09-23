#/bin/sh
# NOTE: encounters.json can't be imported via the importer
mongoimport --db openeyes --collection lasers --file lasers.json
mongoimport --db openeyes --collection patients --file patients.json
mongoimport --db openeyes --collection procedures --file procedures.json
mongoimport --db openeyes --collection sites --file sites.json
mongoimport --db openeyes --collection siteslasers --file siteslasers.json
mongoimport --db openeyes --collection workflows --file workflows.json

