# PowerShell script to clean compiled files and organize project structure
# Maintains proper separation between source (.java) and compiled (.class) files

$ErrorActionPreference = 'Stop'

Write-Host "Cleaning project structure..."

# Remove any .class files that might be in the root directory
Remove-Item "*.class" -Force -ErrorAction SilentlyContinue

# Remove any .class files that might be in the src directory (maintain separation)
Remove-Item "src\*.class" -Force -ErrorAction SilentlyContinue

# Clean the build directory
if (Test-Path "build") {
    Remove-Item -Path "build\*.class" -Force -ErrorAction SilentlyContinue
    Write-Host "Build directory cleaned."
} else {
    Write-Host "Build directory doesn't exist, creating it..."
    New-Item -ItemType Directory -Path "build" -Force
}

Write-Host "✅ Project structure cleaned successfully!"
Write-Host "📁 Source files: src/*.java"
Write-Host "📁 Compiled files will go to: build/*.class"
