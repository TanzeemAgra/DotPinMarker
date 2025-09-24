# Dot Pin Marker Framework

## Project Structure

This project follows a clean separation between source and compiled files:

```
DotPinMakrerFrameWrok/
├── src/                    # Source files (.java only)
│   ├── MarkingInterfaceApp.java    # Main application
│   ├── DrawingCanvas.java          # Canvas component
│   ├── MarkPanel.java              # Control panel
│   ├── Mark.java                   # Base mark class
│   ├── AvoidPointMark.java         # Concentric circles feature
│   ├── FarziMark.java              # Vector text feature
│   ├── ArcLettersMark.java         # Arc letters
│   ├── DotMatrixMark.java          # Dot matrix marks
│   ├── TextMark.java               # Text marks
│   ├── LineMark.java               # Line marks
│   ├── RectangleMark.java          # Rectangle marks
│   ├── BowTextMark.java            # Bow text marks
│   └── TextMarkNew.java            # Enhanced text marks
├── build/                  # Compiled files (.class only)
│   └── *.class                     # Generated during compilation
├── lib/                    # JavaFX libraries
│   └── *.jar                       # JavaFX JAR files
├── build.ps1               # Compilation script
├── run.ps1                 # Application launcher
├── clean.ps1               # Cleanup script
└── README.md               # This file
```

## Build Process

### 1. Compile the project
```powershell
.\build.ps1
```
This compiles all `.java` files from `src/` to `build/` directory.

### 2. Run the application
```powershell
.\run.ps1
```
This launches the Dot Pin Marker Framework application.

### 3. Clean build files
```powershell
.\clean.ps1
```
This removes all `.class` files and ensures proper project organization.

## Development Workflow

1. **Edit source files** in the `src/` directory
2. **Compile** using `.\build.ps1`
3. **Test** using `.\run.ps1`
4. **Clean** using `.\clean.ps1` when needed

## Key Features

- **Avoid Point Marks**: Concentric circles for CNC marking
- **Farzi Text**: Single-stroke vector text
- **Arc Letters**: Curved text along arcs
- **Dot Matrix**: Grid-based marking patterns
- **Various Shapes**: Lines, rectangles, and text marks

## File Organization Rules

- **✅ DO**: Keep only `.java` files in `src/`
- **✅ DO**: Let build process handle `.class` files in `build/`
- **❌ DON'T**: Mix `.java` and `.class` files in the same directory
- **❌ DON'T**: Put `.class` files in the root directory

This organization ensures clean development and easy maintenance.
