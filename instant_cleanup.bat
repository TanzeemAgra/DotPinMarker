@echo off
echo 🧹 INSTANT PROJECT CLEANUP - Soft-Coded Structure Management
echo ==========================================================

echo.
echo 🔍 Analyzing current project structure...

REM Count current files
for /f %%i in ('dir /b /a-d src\*.java 2^>nul ^| find /c /v ""') do set SRC_JAVA=%%i
for /f %%i in ('dir /b /a-d src\*.class 2^>nul ^| find /c /v ""') do set SRC_CLASS=%%i

echo 📊 BEFORE CLEANUP:
echo    src\*.java files: %SRC_JAVA%
echo    src\*.class files: %SRC_CLASS%

if %SRC_CLASS% EQU 0 (
    echo.
    echo ✅ PROJECT ALREADY CLEAN!
    echo 🎯 No .class files found in src directory
    echo 📁 Perfect project structure maintained
    goto :end
)

echo.
echo 🚚 MOVING .CLASS FILES TO BUILD DIRECTORY...
echo ============================================

REM Create build directory
if not exist build (
    mkdir build
    echo 📁 Created build\ directory
)

REM Move all .class files with progress indication
echo 🔄 Moving %SRC_CLASS% .class files...
set MOVED_COUNT=0

for %%f in (src\*.class) do (
    set /a MOVED_COUNT+=1
    echo    [!MOVED_COUNT!/%SRC_CLASS%] Moving %%~nxf
    move "%%f" build\ >nul 2>&1
)

echo.
echo 📊 CLEANUP COMPLETE!
echo ====================

REM Count files after cleanup
for /f %%i in ('dir /b /a-d src\*.java 2^>nul ^| find /c /v ""') do set FINAL_JAVA=%%i
for /f %%i in ('dir /b /a-d src\*.class 2^>nul ^| find /c /v ""') do set FINAL_CLASS_SRC=%%i
for /f %%i in ('dir /b /a-d build\*.class 2^>nul ^| find /c /v ""') do set FINAL_CLASS_BUILD=%%i

echo 📁 AFTER CLEANUP:
echo    src\*.java files: %FINAL_JAVA%
echo    src\*.class files: %FINAL_CLASS_SRC%
echo    build\*.class files: %FINAL_CLASS_BUILD%

echo.
if %FINAL_CLASS_SRC% EQU 0 (
    echo 🎯 SUCCESS: PERFECT PROJECT STRUCTURE!
    echo ✅ Source directory: Clean (Java only)
    echo ✅ Build directory: Complete (Class files)
    echo.
    echo 🚀 PROJECT IS NOW READY FOR:
    echo    - Clean version control
    echo    - Professional development
    echo    - Easy maintenance
    echo.
    echo 💡 To compile and run:
    echo    smart_build.bat
    echo    java -cp "build" SimpleVisibleInterface
) else (
    echo ⚠️ WARNING: Some .class files may still remain
    echo 🔧 You may need to check manually for nested directories
)

:end
echo.
echo 📋 CLEANUP OPERATION COMPLETE!
pause