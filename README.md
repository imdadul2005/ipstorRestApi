# ipstorRestApi

It is Java library that interact with IPStor API. This library can be used for GET,POST,DELETE,and PUT method. 

# Dependencies
1. JAVA
2. Maven

# How to use it ? 
Below parameters need to be send when running it from maven,
1. Dserver
2. Dusername [if different from default user]
3. Dpassword [if different from default password]
4. Drepeat [number of request]
5. Duri [uri after ipstor\]
6. Dpayload [For post method, payload must be saved into a separate file under testFiles directory, and pass the file name to this parameter]
7. Dmethod [# default is "GET"]

# Example of uses:
```maven
mvn test -Dserver=10.8.25.34 -Duri="physicalresource/storagepool"
mvn test -Dserver=10.8.25.34 -Duri="physicalresource/storagepool" -Dmethod=post -Dpayload=test.json
mvn test -Dserver=10.6.11.113 -Duri="logicalresource/configurationrepository" -Dmethod=delete
mvn test -Dserver=10.6.11.113 -Duri="logicalresource/configurationrepository/reconfigure" -Dmethod=put -Dpayload=configRepo.txt
```

# Update : Log4j2 is added for logger information
  With the log4j2 added on this module, now all the logs will be saved into /logs/msg.log files. The msg.log file will be rotated once the file size reach 50MB.  
  
