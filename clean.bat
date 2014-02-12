@ECHO OFF
echo  =====================================
echo  CLEANS OUTPUT FOLDER AND GRADLE CACHE
echo  =====================================
pause
echo Removing output directory...
rmdir /Q /S output
echo Cleaning up Gradle data...
gradlew cleanCache clean
pause