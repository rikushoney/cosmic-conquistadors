# Cosmic Conquistadors

A Space Invaders inspired game written in Java for Computer Science E214.

[![Build Status](https://travis-ci.org/rikushoney/cosmic-conquistadors.svg?branch=master)](https://travis-ci.org/rikushoney/cosmic-conquistadors)

# How to use:

It is recommended to use [Apache Ant](https://ant.apache.org/) to build this project. Download and install it and make sure it is available in your [PATH](<https://en.wikipedia.org/wiki/PATH_(variable)>) before executing commands. To check if ant was installed successfully run `ant -version` and you should see output similar to `Apache Ant(TM) version x.x.x`.

IDE's such as [Eclipse](https://www.eclipse.org/) and [IDEA](https://www.jetbrains.com/idea/) should also include ant and provide ant integration.

To see a list of all available commands run `ant help`.

## Compile project

`ant compile`

or

```bash
mkdir build
mkdir build/classes
javac -cp lib/stdlib.jar -d build/classes src/cosmic_conquistadors/*.java
```

This will compile the game and put the class files in `build/classes`.

## Create jar

`ant jar`

or

```bash
mkdir build/jar
jar cfm build/jar/cosmic-conquistadors.jar src/manifest.txt -C build/classes .
cp lib/*.jar build/jar/
```

This will create a redistributable jar file in `build/jar`.

## Run game

`ant run`

or

```bash
java -jar build/jar/cosmic-conquistadors.jar
```

This will run the jar executable in `build/jar`. Note that ant will automatically compile and create the jar file necessary to run the game.

## Build documentation

`ant doc`

or

```bash
mkdir build/doc
javadoc -cp lib/stdlib.jar src/cosmic_conquistadors/*.java -d build/doc
```

This creates the documentation for the project in `build/doc` ready to be uploaded to the internet.

# Documentation

View the latest documentation [here](https://rikushoney.github.io/cosmic-conquistadors/).
