# PowerShell script to compile JavaFX project
# Compiles all .java files from src/ directory to build/ directory
# Maintains proper separation: src/ = source files, build/ = compiled files

$ErrorActionPreference = 'Stop'

# Try project lib first (new default)
$javaFxLib = Join-Path $PSScriptRoot 'lib'
if (-Not (Test-Path $javaFxLib)) {
    # fallback old location
    $javaFxLib = Join-Path $PSScriptRoot 'javafx\lib'
}

# Override with environment variable or hard-coded path if it exists
if ($env:JAVAFX_LIB) {
    $javaFxLib = $env:JAVAFX_LIB
} elseif (Test-Path 'C:\Users\ASUS\OneDrive\Desktop\DEMO\lib') {
    $javaFxLib = 'C:\Users\ASUS\OneDrive\Desktop\DEMO\lib'
}

if (-Not (Test-Path $javaFxLib)) {
    Write-Error "JavaFX SDK not found. Please download it from https://gluonhq.com/products/javafx/ and extract so that '$javaFxLib' exists."
}

Write-Host "Compiling Java source files from src/ to build/ ..."

# Create build directory if it doesn't exist
if (-Not (Test-Path "build")) {
    Write-Host "Creating build directory..."
    New-Item -ItemType Directory -Path "build"
}

# Compile all .java files from src to build directory
& javac --module-path $javaFxLib --add-modules javafx.controls,javafx.graphics `
       -d build -sourcepath src src\*.java

if ($LASTEXITCODE -eq 0) {
    Write-Host "✅ Compilation successful!"
    Write-Host "📁 Source files: src/*.java"
    Write-Host "📁 Compiled files: build/*.class"
} else {
    Write-Error "❌ Compilation failed!"
}
