@echo off
setlocal enabledelayedexpansion

rem A .java fájlok listájának összegyűjtése
del sources.txt 2>nul
for /r src\main\java %%i in (*.java) do (
    echo %%i >> sources.txt
)

rem A Java fájlok lefordítása
javac -d bin -sourcepath src\main\java @sources.txt

rem A .class fájlok listájának összegyűjtése a bin könyvtárból
del sources.txt 2>nul
for /r bin %%i in (*.class) do (
    echo %%i >> sources.txt
)

rem MANIFEST.MF fájl létrehozása
echo Manifest-Version: 1.0 > MANIFEST.MF
echo Main-Class: tesztelo.Menu >> MANIFEST.MF

rem .jar fájl készítése
jar cmf MANIFEST.MF szkeleton.jar -C bin .

rem Fájl törlés
del sources.txt
del MANIFEST.MF

echo Fordítás és .jar fájl kész!
exit
