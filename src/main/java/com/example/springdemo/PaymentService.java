package com.example.springdemo;

import org.springframework.stereotype.Component;

@Component
public class PaymentService
{
    public void processPayment()
    {
        System.out.println("Your payment has been processed!");
    }
}
