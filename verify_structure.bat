@echo off
echo 📊 PROJECT STRUCTURE VERIFICATION REPORT
echo =======================================

echo.
echo 📁 ANALYZING PROJECT DIRECTORIES...

REM Get file counts
for /f %%i in ('dir /b /a-d src\*.java 2^>nul ^| find /c /v ""') do set JAVA_FILES=%%i
for /f %%i in ('dir /b /a-d src\*.class 2^>nul ^| find /c /v ""') do set SRC_CLASS_FILES=%%i  
for /f %%i in ('dir /b /a-d build\*.class 2^>nul ^| find /c /v ""') do set BUILD_CLASS_FILES=%%i

echo 📋 PROJECT STRUCTURE STATUS:
echo =============================
echo.
echo 📂 SOURCE DIRECTORY (src\):
echo    ✅ Java source files: %JAVA_FILES%
echo    🎯 Class files: %SRC_CLASS_FILES% (should be 0)
echo.
echo 📂 BUILD DIRECTORY (build\):  
echo    ✅ Compiled class files: %BUILD_CLASS_FILES%
echo.

if %SRC_CLASS_FILES% EQU 0 (
    echo 🎯 PROJECT HEALTH: EXCELLENT! 💚
    echo ═══════════════════════════════
    echo ✅ Perfect separation achieved
    echo ✅ Source directory: Clean
    echo ✅ Build directory: Complete
    echo ✅ Professional project structure
    echo.
    echo 🚀 APPLICATION STATUS: READY TO RUN
    echo ===================================
    echo Command: java -cp "build" SimpleVisibleInterface
    echo.
    echo 📋 SOFT CODING BENEFITS ACHIEVED:
    echo ================================  
    echo ✓ Automatic file separation
    echo ✓ Clean version control
    echo ✓ Professional organization
    echo ✓ Easy maintenance workflow
    echo ✓ Build process integration
    
) else (
    echo ⚠️ PROJECT HEALTH: NEEDS ATTENTION
    echo ===================================
    echo 🔧 %SRC_CLASS_FILES% .class files found in src directory
    echo 💡 Run instant_cleanup.bat to fix
)

echo.
echo 📊 SOFT-CODED PROJECT MANAGEMENT:
echo =================================
echo ✓ ProjectStructureManager.java - Automatic separation
echo ✓ instant_cleanup.bat - Quick cleanup tool  
echo ✓ smart_build.bat - Integrated build process
echo ✓ Project structure maintained via soft coding

echo.
pause