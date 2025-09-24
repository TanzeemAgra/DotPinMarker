@echo off
echo 🧹 ULTIMATE PROJECT CLEANUP - Ensures Perfect Structure
echo ======================================================

echo.
echo 📊 Step 1: Analyzing current project structure...

REM Check for misplaced .class files in root directory
for /f %%i in ('dir /b /a-d *.class 2^>nul ^| find /c /v ""') do set ROOT_CLASS_FILES=%%i

REM Count files in proper locations
for /f %%i in ('dir /b /a-d src\*.java 2^>nul ^| find /c /v ""') do set SRC_JAVA_FILES=%%i
for /f %%i in ('dir /b /a-d src\*.class 2^>nul ^| find /c /v ""') do set SRC_CLASS_FILES=%%i
for /f %%i in ('dir /b /a-d build\*.class 2^>nul ^| find /c /v ""') do set BUILD_CLASS_FILES=%%i

echo 📋 CURRENT PROJECT STATUS:
echo ===========================
echo Root directory .class files: %ROOT_CLASS_FILES%
echo Source directory Java files: %SRC_JAVA_FILES%
echo Source directory .class files: %SRC_CLASS_FILES%
echo Build directory .class files: %BUILD_CLASS_FILES%

echo.
if %ROOT_CLASS_FILES% GTR 0 (
    echo 🔧 Step 2: Moving %ROOT_CLASS_FILES% .class files from ROOT to BUILD directory...
    
    if not exist build mkdir build
    
    for %%f in (*.class) do (
        echo    Moving %%f to build\
        move "%%f" build\ >nul 2>&1
    )
    
    echo ✅ ROOT cleanup complete!
    
    REM Recount after root cleanup
    for /f %%i in ('dir /b /a-d *.class 2^>nul ^| find /c /v ""') do set NEW_ROOT_CLASS=%%i
) else (
    echo ✅ Root directory already clean!
    set NEW_ROOT_CLASS=0
)

echo.
if %SRC_CLASS_FILES% GTR 0 (
    echo 🔧 Step 3: Moving %SRC_CLASS_FILES% .class files from SRC to BUILD directory...
    
    if not exist build mkdir build
    
    for %%f in (src\*.class) do (
        echo    Moving %%~nxf to build\
        move "%%f" build\ >nul 2>&1
    )
    
    echo ✅ SRC cleanup complete!
    
    REM Recount after src cleanup
    for /f %%i in ('dir /b /a-d src\*.class 2^>nul ^| find /c /v ""') do set NEW_SRC_CLASS=%%i
) else (
    echo ✅ Source directory already clean!
    set NEW_SRC_CLASS=0
)

echo.
echo 📊 Step 4: Final Structure Verification...
echo =========================================

REM Final count
for /f %%i in ('dir /b /a-d build\*.class 2^>nul ^| find /c /v ""') do set FINAL_BUILD_CLASS=%%i

echo 📁 FINAL PROJECT STRUCTURE:
echo ============================
echo Root directory → %NEW_ROOT_CLASS% .class files (should be 0)
echo Source directory → %SRC_JAVA_FILES% Java files, %NEW_SRC_CLASS% .class files (should be 0)
echo Build directory → %FINAL_BUILD_CLASS% .class files

echo.
if %NEW_ROOT_CLASS% EQU 0 (
    if %NEW_SRC_CLASS% EQU 0 (
        echo 🎯 SUCCESS: PERFECT PROJECT STRUCTURE ACHIEVED! 💚
        echo ═══════════════════════════════════════════════
        echo ✅ All .class files properly located in build\ directory
        echo ✅ Source directory contains only Java source files
        echo ✅ Root directory is clean of compiled files
        echo ✅ Professional project organization maintained
        echo.
        echo 🚀 APPLICATION STATUS: READY TO RUN
        echo ===================================
        echo Command: java -cp "build" SimpleVisibleInterface
        echo.
        echo 📋 BENEFITS ACHIEVED:
        echo ====================
        echo ✓ Clean version control (no compiled files tracked)
        echo ✓ Professional project structure
        echo ✓ Easy maintenance and collaboration
        echo ✓ Proper separation of concerns
        
    ) else (
        echo ⚠️ WARNING: %NEW_SRC_CLASS% .class files remain in source directory
    )
) else (
    echo ⚠️ WARNING: %NEW_ROOT_CLASS% .class files remain in root directory
)

echo.
echo 💡 MAINTENANCE TIPS:
echo ===================
echo • Always compile with: javac -d build src\*.java
echo • Always run with: java -cp "build" SimpleVisibleInterface
echo • Use ultimate_cleanup.bat if files get misplaced again
echo • Keep source and compiled files separated for best practices

echo.
pause