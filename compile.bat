@ECHO OFF
title Compile MineAPI Project
echo ==============================
echo COMPILING MINEAPI USING GRADLE
echo ==============================
echo Removing output directory...
rmdir /Q /S output
echo Starting Buildscript...
gradlew build