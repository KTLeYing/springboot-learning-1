package com.mzl.ticketgrabbing;

import com.mzl.ticketgrabbing.controller.TicketController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TicketGrabbingApplicationTests {

	@Autowired
	private TicketController ticketController;

	@Test
	public void test() {
		int ticketId = 1;
		int num = 3500;
		for (int i = 1 ; i <= num; i++){
			ticketController.reduceCount(ticketId, i);
		}
	}

}
