## Building with maven

To build our project through maven, look at the following steps 

```
$ git clone git://www.github.com/whippytools/whippy-parent.git
$ git clone git://www.github.com/whippytools/whippy-tools.git
$ git clone git://www.github.com/whippytools/whippy-spawn.git
```

```
$ cd whippy-parent
$ mvn clean install
```

```
$ cd whippy-tools
$ mvn clean install assembly:single
```

```
$ cd whippy-spawn
$ mvn clean install assembly:single
```

Happy Contributing! :)