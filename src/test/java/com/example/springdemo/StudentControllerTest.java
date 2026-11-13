package com.example.springdemo;
import com.example.springdemo.service.StudentService;
import com.example.springdemo.StudentController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class StudentControllerTest {
    @Test
    void testGetStudentById() throws Exception{
        // Create a Mock Student Service
        StudentService mockService = Mockito.mock(StudentService.class);

        // Create a Real StudentController and Injecting mockService through its Constructor!
        StudentController studentController = new StudentController(mockService);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();

        //Create a Student Object
        Student student = new Student(1,"Sreya","Java");

        // Configure Mock behavior
        Mockito.when(mockService.getStudentById(1)).thenReturn(student);

        // simulate HTTP requests.
        mockMvc.perform(get("/students/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.studentName").value("Sreya"))
                .andExpect(jsonPath("$.studentCourse").value("Java"))
                .andExpect(jsonPath("$.studentId").value(1));

    }
}