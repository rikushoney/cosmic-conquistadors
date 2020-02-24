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
    $debugArgs=""
    if ($args[1] -eq "debug") {
        $debugArgs="-g:source,lines,vars"
    }

    javac --release 11 -cp "lib/stdlib.jar" -d "target" "src/cosmic_conquistadors/*.java" $debugArgs
}
elseif ($args[0] -eq "run") {
    java -cp "target;lib/stdlib.jar" cosmic_conquistadors.Invaders
}
elseif ($args[0] -eq "docs") {
    javadoc -cp "lib/stdlib.jar" -d "doc" "src/cosmic_conquistadors/*.java"
}
else {
    usage
}
