@ECHO OFF
title Clean Folders and Gradle Cache
echo =====================================
echo CLEANS OUTPUT FOLDER AND GRADLE CACHE
echo =====================================
echo Removing output directory...
rmdir /Q /S output
echo Cleaning up Gradle data...
gradlew cleanCache clean