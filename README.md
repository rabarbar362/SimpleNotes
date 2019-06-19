# Simple Notes

RESTful API webservice that will be responsible for managing and storing in database simple notes (without the UI part).



## what you need to run the project
MariaDB with user/root account with granted privileges
Intellij IDEA with Gradle
Postman

### database

1. Start MariaDB process
`$ sudo systemctl start mariadb`
2. Login
`$ mysql -u root`
3. Create database *simplenotes*
`CREATE DATABASE simplenotes;`
4. Use the database
`USE simplenotes;`

### project
1. Clone the repo (https://github.com/rabarbar362/SimpleNotes) and open it in IntelliJ
2. Open the *application.properties* file and in
````
spring.datasource.username=
spring.datasource.password=
````
provide your login and password to MariaDB

3. Also in the *application.properties* file and make sure that the 5th line is:
````
spring.jpa.hibernate.ddl-auto=create
````
4. Build+run the project
5. In the *application.properties* file change line
````
spring.jpa.hibernate.ddl-auto=create
````
to
````
spring.jpa.hibernate.ddl-auto=update
````

## example usages (in Postman)

1. Get all notes
GET http://localhost:8080/all

2. Get one particular note
GET http://localhost:8080/getnote?id={id}

3. Get version history of particular note
GET http://localhost:8080/history?id={id}

4. Add new note
POST http://localhost:8080/add
select the Body tab in Postman and add keys: "title" and "content" and provide values

5. Modify existing note
PUT http://localhost:8080/modify?id={id}
select the Body tab in Postman and add keys: "title" and "content" and provide new values

6. Remove note
DELETE http://localhost:8080/delete?id={id}


