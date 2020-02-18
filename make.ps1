function usage {
    echo "Usage: make [COMMAND]`r`n"
    echo "COMMAND"
    echo "What operation to perform"
    echo "`tcompile"
    echo "`t`tCompile cosmic conquistadors"
    echo "`trun"
    echo "`t`tRun cosmic conquistadors`r`n"
}

if ($args[0] -eq "compile") {
    javac -cp "src;lib/stdlib.jar" "src/Invaders.java" -d "target"
}
elseif ($args[0] -eq "run") {
    java -cp "target;lib/stdlib.jar" "Invaders"
}
else {
    usage
}
