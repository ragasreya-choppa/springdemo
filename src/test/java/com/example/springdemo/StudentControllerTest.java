package com.example.springdemo;
import com.example.springdemo.service.StudentService;
import com.example.springdemo.StudentController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.springframework.http.MediaType;

public class StudentControllerTest {
    @Test
    void testGetStudentById() throws Exception{
        // Create a Mock Student Service
        StudentService mockService = Mockito.mock(StudentService.class);

        // Create a Real StudentController and Injecting mockService through its Constructor!
        StudentController studentController = new StudentController(mockService);

        // Create a MockMvc Object using MockMvcBuilders.standaloneSetup().
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


    @Test
    void getStudentByIdNotFound() throws Exception{
        // Create a Mock StudentService
        StudentService mockService = Mockito.mock(StudentService.class);

        //Create a real StudentController using the mocked service.
        StudentController studentController=new StudentController(mockService);

        //Create a  MockMvc object using MockMvcBuilders.standaloneSetup()
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(studentController).setControllerAdvice(new GlobalExceptionHandler()).build();

        // configure Mockito
        Mockito.when(mockService.getStudentById(99)).thenThrow(new StudentNotFoundException("Student Not Found!"));

        mockMvc.perform(get("/students/99")).andExpect(status().isNotFound()).andExpect(content().string("Student Not Found!"));

    }

    @Test
    void testCreateStudent() throws Exception{
        StudentService mockService = Mockito.mock(StudentService.class);
        StudentController studentController = new StudentController(mockService);
        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(studentController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        Student savedStudent = new Student(1,"Sreya","Java");

        //Configure Mockito
        Mockito.when(mockService.createStudent(Mockito.any(Student.class))).thenReturn(savedStudent);

        String jsonBody = """
                {
                "studentName" : "Sreya",
                "studentCourse" : "Java"
                }
                """;

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.studentName").value("Sreya"))
                .andExpect(jsonPath("$.studentCourse").value("Java"))
                .andExpect(jsonPath("$.studentId").value(1));

    }

    @Test
    void testUpdateStudent() throws Exception {
        StudentService mockService = Mockito.mock(StudentService.class);

        StudentController studentController = new StudentController(mockService);

        MockMvc mockMvc  = MockMvcBuilders.standaloneSetup(studentController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        Student updatedStudent = new Student(1,"Sreya","SpringBoot");

        Mockito.when(mockService.updateStudent(Mockito.eq(1),Mockito.any(Student.class))).thenReturn(updatedStudent);

        String jsonBody = """
                {
                "studentName" : "Sreya",
                "studentCourse" : "SpringBoot"
                }
                """;


        mockMvc.perform(put("/students/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentId").value(1))
                .andExpect(jsonPath("$.studentName").value("Sreya"))
                .andExpect(jsonPath("$.studentCourse").value("SpringBoot"));

    }


}