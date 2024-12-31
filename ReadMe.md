```
javac -cp lib/jogamp-all-platforms/jar/jogl-all.jar:lib/jogamp-all-platforms/jar/gluegen-rt.jar -d build src/spaceInvader/**/*.java src/spaceInvader/*.java

```

```
java -cp build:lib/jogamp-all-platforms/jar/jogl-all.jar:lib/jogamp-all-platforms/jar/gluegen-rt.jar -Djava.library.path=lib/jogamp-all-platforms/lib spaceInvader.Fenetre
```
