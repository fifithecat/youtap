# youtap
<br />
build docker image
<br />
docker build -t youtap-interview-johnny .
<br />
<br />
run docker image
<br />
docker run --rm -p 8080:8080 youtap-interview-johnny
<br />
<br />
or
<br />
<br />
build the jar by maven
<br />
mvn clean install
<br />
<br />
run the jar
<br />
"java -jar youtap-interview-0.0.1-SNAPSHOT.jar"
<br />
<br />
<br />
get user contact by user ID
<br />
http://localhost:8080/api/getusercontacts?id=1
<br />
<br />
get user contact by username
<br />
http://localhost:8080/api/getusercontacts?username=max
<br />
<br />
error if no existing contact with provided user ID
<br />
http://localhost:8080/api/getusercontacts?id=0
<br />
<br />
error if no existing contact with provided username
<br />
http://localhost:8080/api/getusercontacts?username=max
<br />
<br />
error if request without user ID name username
<br />
http://localhost:8080/api/getusercontacts
<br />
<br />
error if request with both user ID and username
<br />
http://localhost:8080/api/getusercontacts?id=0&username=max
<br />
<br />
error if provide invalid input for user ID
<br />
http://localhost:8080/api/getusercontacts?id=p
