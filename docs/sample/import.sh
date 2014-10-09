#/bin/sh
#
echo "Dropping existing Mongo database"
mongo openeyes --eval "db.dropDatabase();"

patients=("54229a9f6c5873493a28b3b8" "54229a9f6c5873493a28b3b9" "54229a9f6c5873493a28b3ba" "54229a9f6c5873493a28b3bb" "54229a9f6c5873493a28b3bc" "54229a9f6c5873493a28b3bd" "54229a9f6c5873493a28b3d6" "54229a9f6c5873493a28b3d7" "54229a9f6c5873493a28b3d8" "54229a9f6c5873493a28b3d9" "54229a9f6c5873493a28b3da" "54229a9f6c5873493a28b3db")

for p in "${patients[@]}"
do
  echo "Importing encounters for $p"
  mongoimport --db openeyes --collection encounters --file encounters/$p.json
done

echo "Importing patients"
mongoimport --db openeyes --collection patients --file patients.json

echo "Importing tickets"
mongoimport --db openeyes --collection tickets --file tickets.json

echo "Importing workflows"
mongoimport --db openeyes --collection workflows --file workflows.json
