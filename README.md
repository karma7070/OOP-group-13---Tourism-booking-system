DO THIS BEFORE PULLING!!! 

I created a base file, all you guys have to do is install jdk26 from adoptium and maven from maven.apache.org, the links are below this text

1 - Adoptium: https://adoptium.net 

2 - Maven: https://maven.apache.org/download.cgi

After installing Java and unzipping Maven, you need to tell Windows where to find them:

Press Windows key → search "environment variables" → click "Edit the system environment variables"
Click "Environment Variables" at the bottom
Under "System variables" click "New" and add:

Name: JAVA_HOME
Value: C:\Program Files\Eclipse Adoptium\jdk-21.x.x (use your actual folder name)


Under "System variables" find Path → click Edit → click New and add these two lines:

C:\Program Files\Eclipse Adoptium\jdk-21.x.x\bin
C:\Program Files\apache-maven-3.9.x\bin (use your actual Maven folder name)


Click OK on all windows
Restart VS Code and test in the terminal:

| java -version |
| mvn -version |


Both should print version numbers.
