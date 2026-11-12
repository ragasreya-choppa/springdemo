package com.example.springdemo;
import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "spring_students")
public class Student
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer studentId;

    @NotBlank
    @Column(name="student_name")
    private String studentName;
    @NotBlank
    @Column(name = "student_course")
    private String studentCourse;

    public Student(int studentId, String studentName, String studentCourse)
    {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentCourse = studentCourse;
    }
    public Integer getStudentId()
    {
        return studentId;
    }
    public String getStudentName()
    {
        return studentName;
    }
    public String getStudentCourse()
    {
        return studentCourse;
    }

    public Student(String studentName, String studentCourse)
    {
        this.studentName = studentName;
        this.studentCourse = studentCourse;
    }

    @JsonCreator
    public Student(){}

    public void setStudentId(int studentId)
    {
        this.studentId=studentId;
    }
    public void setStudentName(String studentName){
        this.studentName=studentName;
    }
    public void setStudentCourse(String studentCourse){
        this.studentCourse=studentCourse;
    }

}
