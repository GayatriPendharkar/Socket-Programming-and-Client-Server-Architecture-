Instructions for compilation of the server and client source code:


Server side (applicable for both UDP & TCP)

Execution command:
~$ makefile 
~$ java rscmnd -p port-number (help)

where:
a. makefile will compile the .java file
b. java rscmnd is for running the executable of server source code
c. port-number is the port number at which the server will listen to for connections (use port number greater than 10000 to be in safer side).
d. (optional check) "-h help" will print out how to run the executable



Client side (applicable for all clients)

Execution command:
~$ makefile 
~$ java rccmnd -s server-name -p server-port -c command -n num_exec -d delay (help)

where:
a. java rccmnd is for running the executable of client src code
b. server-name is the domain name of the server
c. server-port is the port number at which server is listening
d. command is the command to be executed by the server
e. num_exec is number of times the command to be executed
f. delay is the delay in consecutive executions
g. (optional check) "-h help" will print out how to run the executable
