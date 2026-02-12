package com.order.mgmt.service;

import com.order.mgmt.service.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class OrderMgmtServiceApplicationTests {

	@Autowired
	private OrderService orderService;

	@Test
	void applicationContextShouldLoadSuccessfully() {
		assertNotNull(orderService);
	}

}
