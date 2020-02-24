#!/bin/bash

usage() {
    printf "Usage: make [COMMAND]\n\n"
    printf "COMMAND\n"
    printf "What operation to perform\n"
    printf "\tcompile\n"
    printf "\t\tCompile cosmic conquistadors\n"
    printf "\trun\n"
    printf "\t\tRun cosmic conquistadors\n\n"
}

if [ "$1" == "compile" ]; then
    debugArgs=""
    if [ "$2" == "debug" ]; then
        debugArgs="-g:source,lines,vars"
    fi

    javac --release 11 -cp lib/stdlib.jar -d target src/cosmic_conquistadors/*.java $debugArgs
elif [ "$1" == "run" ]; then
    java -cp target:lib/stdlib.jar cosmic_conquistadors.Invaders
elif [ "$1" == "docs" ]; then
    javadoc -cp lib/stdlib.jar -d doc src/cosmic_conquistadors/*.java
else
    usage
fi
