# spring-boot-crud
Implementing the spring boot crud operations on student entity by using Postman Rest api

Rest API request for Postman Rest API:

1. Save student:      POST http://localhost:8080/student
2. Get student by Id: GET http://localhost:8080/student/2
3. Get All students:  GET http://localhost:8080/student
4. Update a student:  PUT http://localhost:8080/student/5
5. Delete a student:  DELETE http://localhost:8080/student/6

![image](https://user-images.githubusercontent.com/116196255/215579555-229506d6-6625-4edd-bba0-9a281da09d37.png)
![image](https://user-images.githubusercontent.com/116196255/215579648-a2e27464-3325-4caa-b176-f28812a7fa25.png)
![image](https://user-images.githubusercontent.com/116196255/215579691-f216d5e4-0591-4e97-b1d9-e8675ffebf1f.png)
![image](https://user-images.githubusercontent.com/116196255/215579802-732bb11c-b0b1-40b2-9caa-6ff5ddb7e7b6.png)
![image](https://user-images.githubusercontent.com/116196255/215579845-a49a3543-7b8e-4245-a1db-2b532d1a9aab.png)

_________________________________________________________________________________________________

command prompt and Chrome browser synatx for REST API:

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
