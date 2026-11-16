package com.example.springdemo;

import com.example.springdemo.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.JsonNode;import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class StudentIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    @Transactional
    void testCreateStudent() throws Exception{
        String jsonBody = """
                {
                "studentName" : "Manu",
                "studentCourse" : "SpringBoot"
                }
                """;

        MvcResult result = mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.studentName").value("Manu"))
                .andExpect(jsonPath("$.studentCourse").value("SpringBoot"))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        System.out.println(responseBody);

        JsonNode jsonNode = objectMapper.readTree(responseBody);
        int studentId = jsonNode.get("studentId").asInt();
        System.out.println("Extracted Student Id: "+ studentId);

        Optional<Student> savedStudent = studentRepository.findById(studentId);
        assertTrue(savedStudent.isPresent());

        assertEquals("Manu",savedStudent.get().getStudentName());
        assertEquals("SpringBoot", savedStudent.get().getStudentCourse());

    }

    @Test
    @Transactional
    void testUpdateStudent() throws Exception{
        // create and save student
        Student student = new Student("Manu","Java");
        Student savedStudent = studentRepository.save(student);

        // prepare updated student details
        String jsonBody = """
                {
                "studentName" : "Manu",
                "studentCourse" : "SpringBoot"
                }
                """;

        // send PUT request and verify response
        mockMvc.perform(put("/students/{id}",savedStudent.getStudentId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentId").value(savedStudent.getStudentId()))
                .andExpect(jsonPath("$.studentName").value("Manu"))
                .andExpect(jsonPath("$.studentCourse").value("SpringBoot"));

        // retrieve the updated student
        Optional<Student> updatedStudent = studentRepository.findById(savedStudent.getStudentId());

        //verify the database values
        assertTrue(updatedStudent.isPresent());
        assertEquals("Manu",updatedStudent.get().getStudentName());
        assertEquals("SpringBoot",updatedStudent.get().getStudentCourse());

    }

    @Test
    @Transactional
    void testDeleteStudent() throws Exception{
        Student student = new Student("Sreya","Java");
        Student savedStudent = studentRepository.save(student);

        Integer studentId = savedStudent.getStudentId();

        assertTrue(studentRepository.existsById(studentId));

        mockMvc.perform(delete("/students/{id}",studentId)).andExpect(status().isNoContent());

        assertFalse(studentRepository.existsById(studentId));

    }

    @Test
    @Transactional
    void testCreateStudentWithBlankName() throws Exception{

        // create a JSON with Blank studentName
        String jsonBody = """
                {
                "studentName" : " ",
                "studentCourse" : "Java"
                }
                """;


        //  count students before the request
        long countBefore = studentRepository.count();


        // send POST request with invalid data using MockMvc
        mockMvc.perform(post("/students").contentType(MediaType.APPLICATION_JSON).content(jsonBody)).andExpect(status().isBadRequest());

        // count students after the request
        long countAfter = studentRepository.count();

        // verify no students recorded
        assertEquals(countBefore,countAfter);

    }


    @Test
    @Transactional
    void testCreateStudentWithBlankCourse() throws Exception{

        String jsonBody = """
                {
                "studentName" : "Sreya",
                "studentCourse" : ""
                }
                """;

        long countBefore = studentRepository.count();

        mockMvc.perform(post("/students").contentType(MediaType.APPLICATION_JSON).content(jsonBody)).andExpect(status().isBadRequest());

        long countAfter = studentRepository.count();

        assertEquals(countBefore, countAfter);

    }

}
