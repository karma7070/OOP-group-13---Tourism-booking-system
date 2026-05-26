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


I'm trying to get as much work done as possible so please you guys do whatever you can too and don't forget to branch before pushing




------------------------------------------------------------------------------------------------------------------------------

## Project Structure

```
src/main/java/com/example/TouristSystem/
├── model/
│   ├── User.java
│   └── Touristsite.java
├── repository/
│   ├── UserRepository.java
│   └── TouristSiteRepository.java
├── service/
│   ├── UserService.java
│   └── TouristSiteService.java
├── controller/
│   ├── UserController.java
│   └── TouristSiteController.java
└── DemoApplication.java
```

### `model/`
Contains the data classes that represent database tables. Each class maps directly to a table in PostgreSQL. For example `User.java` maps to the `users` table and `Touristsite.java` maps to the `tourist_sites` table. These classes are marked with `@Entity` which tells Spring Boot to automatically create and manage the corresponding database table.

### `repository/`
Contains interfaces that handle all communication with the database. Each repository extends `JpaRepository` which automatically provides basic database operations such as saving, finding, updating and deleting records without writing any SQL manually.

### `service/`
Contains the business logic of the application. Services sit between the controller and repository layers. They process data, apply rules such as calculating total booking prices, and call the repository when data needs to be saved or retrieved.

### `controller/`
Contains the REST API endpoints. Controllers receive incoming HTTP requests from a client such as Postman or a frontend application, pass them to the appropriate service for processing, and return the result as a JSON response.

### `DemoApplication.java`
The entry point of the application. This is the main class that starts the Spring Boot application and connects everything together.

---

### How the layers interact

````
Client Request → Controller → Service → Repository → PostgreSQL
                                                          ↓
Client Response ← Controller ← Service ← Repository ←←←←←
````

Each layer has a single responsibility and only communicates with the layer directly next to it. The Controller never directly accesses the database — it always goes through the Service first.








----------------------------------------------------------
Sent what might be my final push, I tested a few API paths but just to be safe y'all can test the others too and check among the new pushed files for a TouristBookingSystem_Backend_Report.docx file and read it to understand how the backend works, thanks.
-----------------------------------------------------------------
