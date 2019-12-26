# Chat bot   
A Chat bot app using Dialogflow and 1C:Enterprise.

# Requirements  
* Java 1.8
* Postgresql
* 1C:Enterprise (any trade solution)
* Maven (for building)

# Build Instructions

To build the project, run the command below.

`mvn package`

This command will build the project and package it into an executable, shaded JAR. A shaded JAR file contains all of the project's dependencies. The shaded JAR file is saved here: `target/ChatBot-VERSION.jar`.

# Deploy Instructions

1. Copy the following files to the server. Put them in the same directory:
   1. `target/ChatBot-VERSION-fat.jar`: The executable, shaded JAR file that contains chat-bot's code and dependencies.
1. Run chat-bot: `java -jar ChatBot-VERSION-fat.jar`
