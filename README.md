Electricity billing system
=======================
I make this project for my own improvement.

I use the following things:
-  Spring Boot
-  Docker
-  JDK Corretto-17.0.7
-  Jacoco
-  Gradle

How to run this project
--------

1) Firstly, you need to run the ***docker-compose.yml*** file because this create a docker container.
In this container you can find the database.
2) Then you can run the project


Please note that **the database is empty**, so you need to put some data in it!
- First you need to register clients.
- Second add consumption to the clients.

You can easily add data to the database with **Swagger**.

Here you can find the endpoints: http://localhost:8080/swagger-ui/index.html#/

Tests
------
Also, I make some test. If you run gradle **verification/test** after that Jacoco generate a test report
in the ***build/jacocoHtml*** folder. There you can open the ***index.html*** file in your browser and see the tests coverage. 
