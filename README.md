# Openeyes #

## Build & Run ##

```sh
$ cd Openeyes
$ ./sbt
> container:start
> browse
```

If `browse` doesn't launch your browser, manually open [http://localhost:8080/](http://localhost:8080/) in your browser.

To auto compile when anything is updated and saved also run this in the SBT console:

```sh
~ ;copy-resources;aux-compile
```

## Dependencies ##

* mongodb

## Sample Data ##

There are mongodb objects in the directory `docs/sample`. You can load them by running:

```sh
cd docs/sample/ && ./import.sh
```

## Versioning ##

IMPORTANT! 

If you update the version number in build.scala you will also need to make sure that you update the current version in config/deploy.rb.

This is because the deploy task needs to know the version of the WAR file it has to copy across. At some point this should be made better but for now it is what it is.