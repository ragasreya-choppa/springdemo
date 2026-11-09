package com.example.springdemo;
import com.example.springdemo.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.example.springdemo.repository.StudentRepository;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentServiceTest {

    @Test
    void testMockRepository(){
        StudentRepository mockRepository = Mockito.mock(StudentRepository.class);

        StudentService studentService = new StudentService(mockRepository);

        Student student = new Student(2,"Sreya","Spring Boot Development");

        Mockito.when(mockRepository.findById(2)).thenReturn(Optional.of(student));

        Optional<Student> result = mockRepository.findById(2);

        Student actualStudent = studentService.getStudentById(2);

        assertEquals(2,actualStudent.getStudentId());

        assertEquals("Sreya",actualStudent.getStudentName());

        assertTrue(result.isPresent());

        assertEquals(2,result.get().getStudentId());

        assertEquals("Sreya",result.get().getStudentName());

    }
}
