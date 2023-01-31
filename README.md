# spring-boot-crud
Implementing the spring boot crud operations on student entity by using Postman Rest api

Rest API request for Postman Rest API:

1. Save student:      POST http://localhost:8080/student
2. Get student by Id: GET http://localhost:8080/student/2
3. Get All students:  GET http://localhost:8080/student
4. Update a student:  PUT http://localhost:8080/student/5
5. Delete a student:  DELETE http://localhost:8080/student/6

![image](https://user-images.githubusercontent.com/116196255/215650470-713e4132-c0bc-450f-98cc-a1dc48de6edf.png)
![image](https://user-images.githubusercontent.com/116196255/215650598-86a20c11-1527-4b5b-9d7c-ba6be8046daf.png)
![image](https://user-images.githubusercontent.com/116196255/215650650-defc21ed-9141-4ec5-bb34-34c26371f996.png)
![image](https://user-images.githubusercontent.com/116196255/215650923-791735b4-d4ca-4902-a8b2-16d035d13a1f.png)
![image](https://user-images.githubusercontent.com/116196255/215651029-efc30f06-3430-4536-92ac-b52f915820c2.png)

_________________________________________________________________________________________________

Command Prompt and Chrome or any browser cURL syntax for REST API:

1. Save student: curl -X POST -H "Content-Type: application/json" -d "{\"name\":\"milind\",\"email\":\"milind@gmail.com\"}" http://localhost:8080/student
2. Get student by Id: curl http://localhost:8080/student/1
3. Get All students: curl http://localhost:8080/student
4. Update a student: curl -X PUT -H "Content-Type: application/json" -d "{\"id\":23,\"name\":\"karan\",\"email\":\"milind@gmail.com\"}" http://localhost:8080/student/23 -v
5. Delete a student: curl -X DELETE http://localhost:8080/student/24

note:
1. cURL is a networking tool used to transfer data between a server and the curl client using protocols like HTTP, FTP, TELNET, and SCP.
2. cURL is a command-line tool that lets you transmit HTTP requests and receive responses from the command line or a shell script. It is available for Linux distributions, Mac OS X, and Windows.
3. cURL command syntax is used to construct the command in order to use cURL to run REST web API call: https://<HOST_OR_IP>/<BASE_INSTALL_DIR>/rest/
4. reference website for cURL application: https://developer.adobe.com/commerce/webapi/get-started/gs-curl/
![image](https://user-images.githubusercontent.com/116196255/215577977-731f1bb8-252b-436f-b67b-01a2375fc24b.png)
