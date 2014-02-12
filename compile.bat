@ECHO OFF
echo  =====================================
echo  COMPILES MADSCIENCE USING FORGEGRADLE
echo  =====================================
pause
echo Removing output directory...
rmdir /Q /S output
echo Starting Buildscript...
gradlew clean build
pause