package com.example.springdemo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.Mockito;

public class CalculatorTest {
    @Test
    void testAddition(){
        int result = 10+20;

        assertEquals(30,result);
    }
}
