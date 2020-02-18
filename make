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
    javac -cp lib/stdlib.jar src/Invaders.java -d target
elif [ "$1" == "run" ]; then
    java -cp target:lib/stdlib.jar Invaders
else
    usage
fi
