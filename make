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

    javac -cp src:lib/stdlib.jar src/Invaders.java -d target --release 11 $debugArgs
elif [ "$1" == "run" ]; then
    java -cp target:lib/stdlib.jar Invaders
else
    usage
fi
