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

    javac -cp "src;lib/stdlib.jar" "src/Invaders.java" -d "target" --release 11 $debugArgs
}
elseif ($args[0] -eq "run") {
    java -cp "target;lib/stdlib.jar" "Invaders"
}
else {
    usage
}
