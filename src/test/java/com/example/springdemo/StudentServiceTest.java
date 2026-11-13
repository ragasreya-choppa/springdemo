package com.example.springdemo;
import com.example.springdemo.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.example.springdemo.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;


public class StudentServiceTest {

    @Test
    void testMockRepository()
    {
        // step 1: Create a Mock StudentRepository
        StudentRepository mockRepository = Mockito.mock(StudentRepository.class);

        // step 2: create real student service
        StudentService studentService = new StudentService(mockRepository);

        // step 3: create Test data
        Student student = new Student(2,"Sreya","Spring Boot Development");

        // step 4: configure the Mock behavior
        Mockito.when(mockRepository.findById(2)).thenReturn(Optional.of(student));

        // step 5: call real StudentService method
        Student actualStudent = studentService.getStudentById(2);

        // step 6: verify returned values
        assertEquals(2,actualStudent.getStudentId());

        assertEquals("Sreya",actualStudent.getStudentName());

        // step 7: verify repository interaction
        Mockito.verify(mockRepository, Mockito.times(1)).findById(2);

    }


    @Test
    void testStudentNotFoundException() {

        StudentRepository mockRepository = Mockito.mock(StudentRepository.class);

        StudentService studentService = new StudentService(mockRepository);

        Mockito.when(mockRepository.findById(99)).thenReturn(Optional.empty());

        StudentNotFoundException exception = assertThrows(
                StudentNotFoundException.class,
                ()-> studentService.getStudentById(99)
        );

        assertEquals("Student Not Found!", exception.getMessage());

    }

    @Test
    void testCreateStudent(){
        // create a mock StudentRepository
        StudentRepository mockRepository = Mockito.mock(StudentRepository.class);

        // call a real StudentService using the mock StudentRepository created above!
        StudentService studentService = new StudentService(mockRepository);

        // create a Student object with the name "Sreya" and course "Java" using two-arg constructor.
        Student student = new Student("Sreya","Java");

        // configure Mockito's save() behavior
        Mockito.when(mockRepository.save(student)).thenReturn(student);

        // call real student method
        Student savedStudent = studentService.createStudent(student);

        // verify the returned results
        assertEquals("Sreya", savedStudent.getStudentName());
        assertEquals("Java", savedStudent.getStudentCourse());

        Mockito.verify(mockRepository, Mockito.times(1)).save(student);

    }

    @Test
    void testDeleteStudent(){
        // create a mock repository
        StudentRepository mockRepository = Mockito.mock(StudentRepository.class);

        // call a real StudentService using the mock StudentRepository created above!
        StudentService studentService = new StudentService(mockRepository);


        //Student student = new Student(1,"Sreya","Spring Boot Development");
        // For testing this delete method - We don't need above statement! because We don't need to create a student object for this
        // test because we're only checking whether the student exists, not retrieving the student details.


        // Configure existsById(1) to return true
        Mockito.when(mockRepository.existsById(1)).thenReturn(true);

        // call real StudentService deleteStudent() method.
        studentService.deleteStudent(1);

        Mockito.verify(mockRepository, Mockito.times(1)).deleteById(1);

    }

    @Test
    void testDeleteStudentNotFound(){

        // Step 1: Create mock StudentRepository
        StudentRepository mockRepository = Mockito.mock(StudentRepository.class);

        // Step 2: Create real StudentService
        StudentService studentService = new StudentService(mockRepository);

        // Step 3: Configure existsById(99) to return false
        Mockito.when(mockRepository.existsById(99)).thenReturn(false);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                ()-> studentService.deleteStudent(99)
        );

        assertEquals(
          HttpStatus.NOT_FOUND,
          exception.getStatusCode()
        );

        Mockito.verify(mockRepository, Mockito.never()).deleteById(99);

    }

    @Test
    void testUpdateStudent(){

        // Step 1: Create mock StudentRepository
        StudentRepository mockRepository = Mockito.mock(StudentRepository.class);

        // Step 2: Create real StudentService
        StudentService studentService = new StudentService(mockRepository);

        // Step 3: Create existing student
        Student existingStudent = new Student(1,"Sreya","Java");

        // Step 4: Create updated student
        Student updatedStudent = new Student("Sreya R","Spring Boot");

        Mockito.when(mockRepository.findById(1)).thenReturn(Optional.of(existingStudent));

        Mockito.when(mockRepository.save(existingStudent)).thenReturn(existingStudent);

        Student result = studentService.updateStudent(1,updatedStudent);

        assertEquals(1,result.getStudentId());

        assertEquals("Sreya R",result.getStudentName());

        assertEquals("Spring Boot",result.getStudentCourse());

        Mockito.verify(mockRepository, Mockito.times(1)).save(existingStudent);
    }



    @Test
    void testUpdateStudentNotFound(){

        // Step 1: Create mock StudentRepository
        StudentRepository mockRepository = Mockito.mock(StudentRepository.class);

        // Step 2: Create real StudentService
        StudentService studentService = new StudentService(mockRepository);

        // Step 3: Create updatedStudent
        Student updatedStudent = new Student("Sreya","SpringBoot");

        // Step 4: Configure findById(99)
        //         to return Optional.empty()
        Mockito.when(mockRepository.findById(99)).thenReturn(Optional.empty());

        ResponseStatusException exception=assertThrows(
                ResponseStatusException.class,
                ()-> studentService.updateStudent(99,updatedStudent)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        Mockito.verify(mockRepository, Mockito.never())
                .save(Mockito.any(Student.class));

    }

}




