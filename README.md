#System Search

##Design Goals
* crawl quickly through the filesystem
* execute commands and queries on the filesystem

##Pattern of Execution
####Query Phase
* traverses the FS
* executes the query on each path 
* logs paths returned by the query
####Command Phase
* optional execution of commands on the paths collected during the query phase.

##Features
####Queries for:
* Filenames with a given extension
    * .class
    * .txt
    * .exe
    * .gif / .jpeg / .jpg / .png
* Files that have not been changed for a given time period

####Commands for:
* Deleting files
* Moving files to different locations
* Potentially scope for the above commands to act on directories 

##Design Goals
1. Application must be able to query the file system and emit data to file or to a data structure
2. Application must be able to read information from a file or data structure and create commands
3. Application must be able to execute commands on the file system and emit the results to file or to a data structure
4. The application should, in some way, present information in a console or GUI
5. Most importantly, SprintFS must be reusable insofar as it can be called from any interface 
