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
    Start-Process javac -ArgumentList "-cp", "lib/stdlib.jar", "src/Invaders.java",  "-d",  "target"
}
elseif ($args[0] -eq "run") {
    Start-Process java  -ArgumentList "-cp", "target;lib/stdlib.jar", "Invaders"
}
else {
    usage
}
