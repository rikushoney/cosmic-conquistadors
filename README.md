# Cosmic Conquistadors
A Space Invaders inspired game written in Java for Computer Science E214.

[![Build Status](https://travis-ci.org/rikushoney/cosmic-conquistadors.svg?branch=master)](https://travis-ci.org/rikushoney/cosmic-conquistadors)

# Project layout
```bash
.
├── lib                 # Libraries that the project depend on
│   └── stdlib.jar      # Provides StdDraw, StdAudio, StdOut, StdIn, etc.
├── LICENSE             # License for this project
├── make                # Make script for Linux/Mac
├── make.cmd            # Make script for Windows
├── make.ps1            # Make script for Powershell
├── README.md           # This file
├── settings.cfg        # File used to save game settings
└── src                 # Project's source code
    └── Invaders.java   # The "main" file which serves as the game's entry point
    └── Config.java     # Class used to load and save game options
```

# How to use:
## Windows
### CMD
Compile project
`make compile`

Run game
`make run`

### Powershell
Compile project
`./make compile`

Run game
`./make run`

## Linux/Mac
Compile project
`./make compile`

Run game
`./make run`
