package com.example.springdemo;

public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(String message)
    {
        super(message);
    }
}
