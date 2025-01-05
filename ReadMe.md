# Space Invaders

Projet 3d space invaders. Le jeu consiste a tué des petits aliens.

On peut se déplacer avec `ZQSD`ou les fleches directionelles. Appuyer sur `Espace` permet de tirer.

Si un enemi vous touche ou si un enemi atteint le bas de l'écran c'est perdu.

## Lancement

Le projet se lance avec ant à l'aide des commandes :

```
ant run
```

ou

```
ant

```

## Compilation

Vous pouvez uniquement compiler à l'aide de :

```
ant build
```

## Nettoyer

Vous pouvez également nettoyer le dossier build à l'aide de :

```
ant clean
```

## Javadoc

Vous pouvez regénerer la javadoc du projet à l'aide de la commande :

```
ant javadoc
```

Une fois générer vous pouvez y accéder en lancant le fichier `doc/index.html`
Commande compilation du projet :

```
javac -cp lib/jogamp-all-platforms/jar/jogl-all.jar:lib/jogamp-all-platforms/jar/gluegen-rt.jar -d build src/spaceInvader/**/*.java src/spaceInvader/*.java

```

Commande de lancement du projet :

```
java -cp build:lib/jogamp-all-platforms/jar/jogl-all.jar:lib/jogamp-all-platforms/jar/gluegen-rt.jar -Djava.library.path=lib/jogamp-all-platforms/lib spaceInvader.Fenetre
```
