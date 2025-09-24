# PowerShell script to run JavaFX project
# Requires classes to be compiled already (run build.ps1 first)

# Try local javafx\lib first
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
} elseif (Test-Path 'C:\Users\ASUS\OneDrive\Desktop\DEMO\lib') {
    $javaFxLib = 'C:\Users\ASUS\OneDrive\Desktop\DEMO\lib'
}

if (-Not (Test-Path $javaFxLib)) {
    Write-Error "JavaFX SDK not found. Please download it and extract so that '$javaFxLib' exists."
}

Write-Host "Running MarkingInterfaceApp ..."

& java --module-path $javaFxLib --add-modules javafx.controls,javafx.graphics --add-exports javafx.graphics/com.sun.javafx.tk=com.sun.javafx.graphics -cp build MarkingInterfaceApp
