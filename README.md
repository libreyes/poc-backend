# Openeyes #

## Build & Run ##

```sh
$ cd Openeyes
$ ./sbt
> container:start
> browse
```

If `browse` doesn't launch your browser, manually open [http://localhost:8080/](http://localhost:8080/) in your browser.


## Dependencies ##

* mongodb

## Sample Data ##

There are mongodb objects in the directory `docs/sample`. You can load them by running:

```
docs/sample/import.sh
```

