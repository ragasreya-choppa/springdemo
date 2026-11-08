package com.example.springdemo;

import org.springframework.stereotype.Service;

@Service
public class OrderService
{
    private final PaymentService paymentService;

    private final PaymentService paymentService1;

    private final EmailService emailService;

    private final InventoryService inventoryService;

    private final ShoppingCart shoppingCart;
    private final ShoppingCart shoppingCart1;


    public OrderService(PaymentService paymentService, PaymentService paymentService1, EmailService emailService, InventoryService inventoryService, ShoppingCart shoppingCart, ShoppingCart shoppingCart1)
    {
        this.paymentService = paymentService;
        this.paymentService1=paymentService1;
        this.emailService = emailService;
        this.inventoryService = inventoryService;
        this.shoppingCart = shoppingCart;
        this.shoppingCart1 = shoppingCart1;
    }

    public void placeOrder()
    {
        /*if(inventoryService.checkProduct())
        {
            paymentService.processPayment();
            emailService.sendEmail();
        }
        else {
            System.out.println("Product Out Of Stock! Order Cannot be Placed");
        }*/

        if(shoppingCart != shoppingCart1)
        {
            System.out.println("They are Prototype Scoped Beans");
        }
        else{
            System.out.println("Not Prototype!");
        }

        if(paymentService ==paymentService1)
        {
            System.out.println("They are Singleton Scoped Beans!");
        }
        else{
            System.out.println("Not Singleton!");
        }

    }

}
