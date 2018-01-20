# ipstorRestApi

It is Java library that interect with IPStor API. This library can be used for GET and POST method only. 

# Dependencies
1. JAVA
2. Maven

# How to use it ? 
Below parameters need to be send when running it from maven,
1. server
2. username [if different from default user]
3. password [if different from default passsword]
4. repeat [number of request]
5. uri [uri after ipstor\]
6. payload [For post method, payload must be saved into a seperate file on root project directory, and pass the file name to this parameter]
7. method [# default is "GET"]

# Example of uses:

  mvn test -Dserver=10.8.25.34 -Duri="physicalresource/storagepool"
  mvn test -Dserver=10.8.25.34 -Duri="physicalresource/storagepool" -Dmethod=post -Dpayload=test.json

# Update : Log4j2 is added for logger information
  With the log4j2 added on this module, now all the logs will be saved into /logs/msg.log files. The msg.log file will be rotated.  
  
