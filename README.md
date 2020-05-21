# System Search

## Current Status
*as of 21/05/2020*
The project is lying dormant for the moment. I intend to revisit it when I am finished with other work and
I can wrap my head around creating a usable console app and GUI app.

The work that went unseen here was my attempt to get a JavaFX 'hello world' app running (which was too painful).
I couldn't get even a simple app to run on my machine.
I think I'll resort to Swing or some other toolset for creating the GUI when the time comes. 

## Design Goals
* crawl quickly through the filesystem
* execute commands and queries on the filesystem

## Pattern of Execution
#### Query Phase
* traverses the FS
* executes the query on each path 
* logs paths returned by the query
#### Command Phase
* optional execution of commands on the paths collected during the query phase.

## Features
#### Queries for:
* Filenames with a given extension
    * .class
    * .txt
    * .exe
    * .gif / .jpeg / .jpg / .png
* Files that have not been changed for a given time period

#### Commands for:
* Deleting files
* Moving files to different locations
* Potentially scope for the above commands to act on directories 

## Design Goals
1. Application must be able to query the file system and emit data to file or to a data structure
2. Application must be able to read information from a file or data structure and create commands
3. Application must be able to execute commands on the file system and emit the results to file or to a data structure
4. The application should, in some way, present information in a console or GUI
5. Most importantly, SprintFS must be reusable insofar as it can be called from any interface 

#### Inspired by
Jason Taylor's Clean Architecture Project: https://github.com/jasontaylordev/CleanArchitecture
