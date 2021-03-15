package com.mzl.ticketgrabbing;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableRabbit
public class TicketGrabbingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketGrabbingApplication.class, args);
	}

}
