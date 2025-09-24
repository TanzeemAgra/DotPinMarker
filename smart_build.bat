@echo off
echo 🏗️ ENHANCED BUILD SCRIPT - Smart Project Structure Management
echo =========================================================

echo.
echo 📊 Step 1: Project Structure Analysis...
echo =========================================

REM Count files before cleanup
for /f %%i in ('dir /b /a-d src\*.java 2^>nul ^| find /c /v ""') do set JAVA_COUNT=%%i
for /f %%i in ('dir /b /a-d src\*.class 2^>nul ^| find /c /v ""') do set CLASS_COUNT=%%i

echo 📁 Current src directory:
echo    Java files: %JAVA_COUNT%
echo    Class files: %CLASS_COUNT%

if %CLASS_COUNT% GTR 0 (
    echo.
    echo 🧹 Step 2: Automatic Structure Cleanup...
    echo ==========================================
    
    echo 🚀 Running Smart Project Structure Manager...
    
    REM Compile and run ProjectStructureManager
    javac -d build src\ProjectStructureManager.java 2>nul
    if exist build\ProjectStructureManager.class (
        java -cp build ProjectStructureManager
    ) else (
        echo 🔧 Manual cleanup - moving .class files...
        
        REM Create build directory if needed
        if not exist build mkdir build
        
        REM Move all .class files from src to build
        for %%f in (src\*.class) do (
            echo    Moving %%~nxf
            move "%%f" build\ >nul 2>&1
        )
        
        echo ✅ Manual cleanup complete!
    )
) else (
    echo ✅ Source directory already clean!
)

echo.
echo ⚙️ Step 3: Compilation Process...
echo ==================================

echo 🔧 Compiling all Java source files...
javac -d build -cp "build;lib\*;." src\*.java

if %ERRORLEVEL% EQU 0 (
    echo ✅ Compilation successful!
    
    echo.
    echo 📊 Step 4: Final Structure Verification...
    echo =========================================
    
    REM Count files after build
    for /f %%i in ('dir /b /a-d src\*.java 2^>nul ^| find /c /v ""') do set FINAL_JAVA=%%i
    for /f %%i in ('dir /b /a-d src\*.class 2^>nul ^| find /c /v ""') do set FINAL_CLASS_SRC=%%i
    for /f %%i in ('dir /b /a-d build\*.class 2^>nul ^| find /c /v ""') do set FINAL_CLASS_BUILD=%%i
    
    echo 📁 FINAL PROJECT STRUCTURE:
    echo ============================
    echo src\     → %FINAL_JAVA% Java files, %FINAL_CLASS_SRC% Class files
    echo build\   → %FINAL_CLASS_BUILD% Class files
    
    if %FINAL_CLASS_SRC% EQU 0 (
        echo.
        echo 🎯 SUCCESS: Perfect project structure achieved!
        echo ✅ Source directory: Clean (Java files only)
        echo ✅ Build directory: Complete (All compiled classes)
        echo.
        echo 🚀 READY TO RUN:
        echo ================
        echo java -cp "build" SimpleVisibleInterface
        echo.
        echo 📋 PROJECT HEALTH: EXCELLENT 💚
        
    ) else (
        echo.
        echo ⚠️ WARNING: %FINAL_CLASS_SRC% .class files remain in src directory
        echo 🔧 Manual intervention may be required
        echo.
        echo 💡 Try running: java -cp build ProjectStructureManager
    )
    
else
    echo.
    echo ❌ COMPILATION FAILED!
    echo ===================
    echo Please check compilation errors above.
    echo 🔍 Common issues:
    echo    - Missing dependencies in lib\ directory
    echo    - Syntax errors in Java files
    echo    - Classpath configuration problems
fi

echo.
echo 📋 BUILD SUMMARY:
echo =================
if %ERRORLEVEL% EQU 0 (
    echo Status: ✅ BUILD SUCCESSFUL
    echo Structure: 🎯 CLEAN SEPARATION ACHIEVED
    echo Ready: 🚀 APPLICATION READY TO RUN
) else (
    echo Status: ❌ BUILD FAILED
    echo Action: 🔧 CHECK ERRORS AND RETRY
fi

echo.
pause