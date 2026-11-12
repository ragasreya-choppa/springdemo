package com.example.springdemo;
import com.example.springdemo.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.example.springdemo.repository.StudentRepository;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;


public class StudentServiceTest {

    @Test
    void testMockRepository()
    {
        // step 1: Create Mock Repository
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

        // step 7: verify repository introduction
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

}
