package com.example.springdemo;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.example.springdemo.service.StudentService;
import jakarta.validation.Valid;
import java.util.*;

@RestController
public class StudentController {
    private final StudentService studentService;
    public StudentController(StudentService studentService)
    {
        this.studentService = studentService;
    }

    @GetMapping("/student")
    public Student getStudent()
    {
       Student student =  new Student(10,"Ananya","Computer Science");
       return student;
    }
    @GetMapping("/students")
    public List<Student> getAllStudents()
    {
//        return students;
        return studentService.getAllStudents();
    }

//    @GetMapping("/student/{id}")
//    public String getStudentId(@PathVariable{int id})
//    {
//        return "Student ID: "+id;
//    }
    // the above code is just for printing path variable

    @GetMapping("/students/{id}")
    public Student getStudentById(@PathVariable int id)
    {
//        for(Student student:students){
//            if(student.getStudentId()==id)
//            {
//                return student;
//            }
//        }
//        throw new ResponseStatusException(
//                HttpStatus.NOT_FOUND,
//                "Student Not Found");
        return studentService.getStudentById(id);
    }

//    @PostMapping("/students/preview")
//    public Map<String, Object> previewStudent(
//            @RequestBody Map<String, Object> studentData){
//        return studentData;
//    }
    // the above code is for demonstrating HTTP POST method using Map

    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student)
    {
//        students.add(student);
//        return ResponseEntity.status(HttpStatus.CREATED).body(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createStudent(student));
    }

    @PutMapping("/students/{id}")
    public Student updateStudent(@PathVariable int id, @Valid @RequestBody Student updatedStudent)
    {
//      for(Student student:students){
//            if(student.getStudentId()==id)
//            {
//                student.setStudentName(updatedStudent.getStudentName());
//                student.setStudentCourse(updatedStudent.getStudentCourse());
//                return student;
//            }
//        }
//        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Student Not Found!");
        return studentService.updateStudent(id,updatedStudent);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable int id)
    {
//        Iterator<Student> iterator = students.iterator();
//        while(iterator.hasNext())
//        {
//            Student student = iterator.next();
//            if(student.getStudentId() == id)
//            {
//                iterator.remove();
//                return;// we use return instead of break- because- break exists only the loop! after executing break; java continues with any statements after the while loop!
//            }
//        }
//        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Student Not Found!");

        studentService.deleteStudent(id);
    }

}
