@echo off

:make
if ["%1"] equ ["compile"] (
    set debugArgs=""
    if ["%2"] equ ["debug"] (
        set debugArgs="-g:source,lines,vars"
    )

    javac -cp src;lib/stdlib.jar src/Invaders.java -d target --release 11 %debugArgs%
    goto :done
) else if ["%1"] equ ["run"] (
    java -cp target;lib/stdlib.jar Invaders
    goto :done
) else (
    goto :usage
)

:usage
echo Usage: make [COMMAND]
echo.
echo COMMAND
echo What operation to perform
echo    compile
echo        Compile cosmic conquistadors
echo    run
echo        Run cosmic conquistadors
echo.

:done
