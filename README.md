# spring-boot-crud
Implementing the spring boot crud operations on student entity by using Postman Rest api

command prompt and Chrome browser synatx for REST API:

1. To save student: curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"milind\",\"email\":\"milind@gmail.com\"}" http://localhost:8080/student
2. To get student by Id: curl http://localhost:8080/student/1
3. To get all students: curl http://localhost:8080/student
4. update a student: curl -X PUT -H "Content-Type: application/json" -d "{\"id\":23,\"name\":\"karan\",\"email\":\"milind@gmail.com\"}" http://localhost:8080/student/23 -v
5. delete a student: curl -X DELETE http://localhost:8080/student/24

note:
reference website for cURL application: https://developer.adobe.com/commerce/webapi/get-started/gs-curl/
a. cURL is a networking tool used to transfer data between a server and the curl client using protocols like HTTP, FTP, TELNET, and SCP.
b. cURL is a command-line tool that lets you transmit HTTP requests and receive responses from the command line or a shell script. It is available for Linux distributions, Mac OS X, and Windows.
c. cURL command syntax is used to construct the command in order to use cURL to run REST web API call: https://<HOST_OR_IP>/<BASE_INSTALL_DIR>/rest/
![image](https://user-images.githubusercontent.com/116196255/215577266-a2908f39-43c0-4505-aa7e-760a8a3b1a1d.png)
