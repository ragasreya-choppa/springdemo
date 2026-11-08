package com.example.springdemo.service;

import com.example.springdemo.Student;
import com.example.springdemo.StudentNotFoundException;
import com.example.springdemo.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StudentService
{
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository)
    {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents()
    {
        return studentRepository.findAll();
    }

    public Student getStudentById(int id)
    {
        logger.debug("Searching for student with ID: {}",id);
        Optional<Student> optionalStudent = studentRepository.findById(id);
        return optionalStudent.orElseThrow(() -> {
            logger.warn("Student not found with ID: {}",id);
            return new StudentNotFoundException("Student Not Found!");
        });
    }


    public Student createStudent(Student student)
    {
        logger.info("Saving Student to DataBase!");
        Student savedStudent = studentRepository.save(student);
        logger.info("Student Saved SuccessFully with ID: {}", savedStudent.getStudentId());
        return savedStudent;
    }


    public Student updateStudent(int id, Student updatedStudent)
    {
        Student student = studentRepository.findById(id).orElse(null);
        if(student == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Student Not Found!");
        }

        student.setStudentName(updatedStudent.getStudentName());
        student.setStudentCourse(updatedStudent.getStudentCourse());
        return studentRepository.save(student);
    }

    public void deleteStudent(int id) {

        boolean deleted = studentRepository.existsById(id);
        if(!deleted){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student Not Found!");
        }
        studentRepository.deleteById(id);
    }
}
