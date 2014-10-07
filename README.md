# Openeyes #

## Build & Run ##

```
$ cd Openeyes
$ ./sbt
> container:start
> browse
```

If `browse` doesn't launch your browser, manually open [http://localhost:8080/](http://localhost:8080/) in your browser.

To auto compile when anything is updated and saved also run this in the SBT console:

```
~ ;copy-resources;aux-compile
```

## Dependencies ##

* Mongo DB
* ImageMagick

## Sample Data ##

There is some sample Mongo DB data that can be found at `docs/sample`. 

These can be imported by running the import script:

```
cd docs/sample/ && ./import.sh
```

To update the data remotely run:

```
ssh openeyes@178.79.188.31 "cd /home/openeyes/public_html/dev/api/current/docs/sample/ && ./import.sh"
```

This will require having a public key installed on the remote server.

## Tomcat ##

In case you need to restart the Tomcat server remotely run:

```
ssh tomcat@178.79.188.31 "cd /home/tomcat/apache-tomcat-7.0.55/bin && ./shutdown.sh ; ./startup.sh"
```

This will require having a public key installed on the remote server.

## Versioning ##

**IMPORTANT!** 

The version number is now pulled from the file `version` found in the root project, and should be set to the latest tag number.

This file is used in both the `build.scala` file and the `config/deploy.rb`.

## DICOM

TODO: Write more docs...

```
./storescu -c OPENEYES@localhost:11112 ~/docs/dicom-files/54229a9f6c5873493a28b3b8.dcm -b HELLO
```
