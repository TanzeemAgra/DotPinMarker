# Project Organization Script
# Use this script to organize your project structure properly

Write-Host "Organizing Dot Pin Marker Framework project..."

# Check current structure
Write-Host ""
Write-Host "Current project structure analysis:"

$srcFiles = Get-ChildItem "src" -Filter "*.java" | Measure-Object
$srcClassFiles = Get-ChildItem "src" -Filter "*.class" -ErrorAction SilentlyContinue | Measure-Object
$buildFiles = Get-ChildItem "build" -Filter "*.class" -ErrorAction SilentlyContinue | Measure-Object

Write-Host "   src/        - $($srcFiles.Count) Java source files"
Write-Host "   src/        - $($srcClassFiles.Count) Class files (should be 0)"
Write-Host "   build/      - $($buildFiles.Count) Compiled class files"

# Move any misplaced .class files from src to build
if ($srcClassFiles.Count -gt 0) {
    Write-Host ""
    Write-Host "Moving $($srcClassFiles.Count) .class files from src/ to build/..."
    Move-Item "src\*.class" "build\" -Force
    Write-Host "   Files moved successfully"
} else {
    Write-Host ""
    Write-Host "No .class files found in src/ directory (good!)"
}

# Clean up any .class files in root directory
$rootClassFiles = Get-ChildItem "." -Filter "*.class" -ErrorAction SilentlyContinue
if ($rootClassFiles.Count -gt 0) {
    Write-Host ""
    Write-Host "Cleaning $($rootClassFiles.Count) .class files from root directory..."
    Remove-Item "*.class" -Force
    Write-Host "   Root directory cleaned"
}

# Ensure build directory exists
if (-Not (Test-Path "build")) {
    Write-Host ""
    Write-Host "Creating build directory..."
    New-Item -ItemType Directory -Path "build" -Force
    Write-Host "   Build directory created"
}

# Final structure verification
Write-Host ""
Write-Host "Final project structure:"
Write-Host "   src/        - Source files (.java only)"
Write-Host "   build/      - Compiled files (.class only)"
Write-Host "   lib/        - JavaFX libraries"
Write-Host "   build.ps1   - Compilation script"
Write-Host "   run.ps1     - Application launcher"
Write-Host "   clean.ps1   - Project cleanup script"

Write-Host ""
Write-Host "Project organization complete!"
Write-Host ""
Write-Host "Recommended workflow:"
Write-Host "   1. Edit source files in src/"
Write-Host "   2. Run .\build.ps1 to compile"
Write-Host "   3. Run .\run.ps1 to launch application"
Write-Host "   4. Run .\clean.ps1 to clean build files"

# List source files for reference
Write-Host ""
Write-Host "Source files in src/:"
Get-ChildItem "src" -Filter "*.java" | ForEach-Object {
    Write-Host "   - $($_.Name)"
}
