package com.example.springdemo;

import com.example.springdemo.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class StudentIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void contextLoads(){

    }

    @Test
    @Transactional
    void testGetStudentById() throws Exception{
        Student student = new Student("Sreya","Java");
        Student savedStudent = studentRepository.save(student);

        mockMvc.perform(get("/students/{id}",savedStudent.getStudentId())).andExpect(status().isOk())
                .andExpect(jsonPath("$.studentName").value("Sreya"))
                .andExpect(jsonPath("$.studentCourse").value("Java"));
    }

}
