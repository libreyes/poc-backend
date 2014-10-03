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
docs/sample/import.sh
```
