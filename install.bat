@ECHO OFF
title Configure New MineAPI Project
echo ================================
echo CONFIGURES ECLIPSE PROJECT FILES
echo ================================
gradlew cleanCache --refresh-dependencies setupDecompWorkspace setupDevWorkspace eclipse build