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
* Files that have not been changed for a given time period
* 