package com.example.springdemo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class NotificationService
{
    private final EmailService emailService;

    public NotificationService(EmailService emailService)
    {
        this.emailService = emailService;
    }

    public void notifyUser()
    {
        emailService.sendEmail();
    }
}
