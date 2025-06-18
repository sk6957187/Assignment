where pest react jar file.

which part write inside DTO package.

Junit not work.

How to save Logger data in file.

2nd level catch implement.


------------API and input data------------------
Add Student
http://localhost:8080/course-enrolments/student
input:-
    "name" : "Aman Kumar",
    "email": "aman@gmail.com",
    "date_Of_Birth":"2005-01-20"

Get all student
http://localhost:8080/course-enrolments/student

get student by id
http://localhost:8080/course-enrolments/student/4

update student
http://localhost:8080/course-enrolments/student/update
    "id":"4",
    "email": "aman1@gmail.com"

delete student by id
http://localhost:8080/course-enrolments/student/3

get course by student id
http://localhost:8080/course-enrolments/student/course/5

Add instructor
http://localhost:8080/course-enrolments/instructor

get all instructor
http://localhost:8080/course-enrolments/instructor

get instructor by id
http://localhost:8080/course-enrolments/instructor/9

update Instructor
http://localhost:8080/course-enrolments/instructor/update
    "id":"2",
    "email": "mohan@gmail.com"

delete Instructor
http://localhost:8080/course-enrolments/instructor/4

get student by instructor
http://localhost:8080/course-enrolments/instructor/student/1

Add course
http://localhost:8080/course-enrolments/course
    "title":"SQL",
    "duration":"2",
    "fee":4000,
    "instructor":{
        "id":"9"
    }

get all course
http://localhost:8080/course-enrolments/course

get course by id
http://localhost:8080/course-enrolments/course/5

update course
http://localhost:8080/course-enrolments/course
    "id":"5",
    "fee":"3500"

delete course by id
http://localhost:8080/course-enrolments/course/2

get course by instructor id
http://localhost:8080/course-enrolments/course/instructor/1

Add enrolment  (how to update enrolment in this method)
http://localhost:8080/course-enrolments/enrolment
"enrolledDate":"2025-02-22",
 "student":{
     "id":"5"
 },
 "course":{
     "id":"5"
 }

Get all enrolment
http://localhost:8080/course-enrolments/enrolment

delete enrolment by id
http://localhost:8080/course-enrolments/enrolment/6

update enrolment by id
http://localhost:8080/course-enrolments/enrolment/4
     "course":{
         "id":"2"
     }

delete enrolment by id
http://localhost:8080/course-enrolments/enrolment/4

get enrolment by student id
http://localhost:8080/course-enrolments/enrolment/student/5

get enrolment by course id
http://localhost:8080/course-enrolments/enrolment/course/3







