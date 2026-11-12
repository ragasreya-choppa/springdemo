package com.example.springdemo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringdemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringdemoApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner demo(ApplicationContext context){
//		return args -> {
//			ShoppingCart cart1 = context.getBean(ShoppingCart.class);
//			ShoppingCart cart2 = context.getBean(ShoppingCart.class);
//
//			System.out.println("Same ShoppingCart object? "+ (cart1==cart2));
//		};
//	}

//	@Bean
//	public CommandLineRunner demo(OrderService orderService)
//	{
//		return args -> {
//			orderService.placeOrder();
//			orderService.placeOrder();
//			orderService.placeOrder();
//		};
//	}

}
