package com.example.transaction.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.transaction.entity.Order;
import com.example.transaction.service.OrderProcessingService;
import com.example.transaction.service.isolation.ReadCommittedDemo;
import com.example.transaction.service.isolation.ReadUncommittedDemo;
import com.example.transaction.service.isolation.RepeatableReadDemo;
import com.example.transaction.service.isolation.SerializableIsolationDemo;

@RestController
@RequestMapping("/api/orders")
public class OrderProcessingController
{

	private final OrderProcessingService orderProcessingService;

	private final ReadUncommittedDemo readUncommittedDemo;

	private final ReadCommittedDemo readCommittedDemo;

	private final RepeatableReadDemo repeatableReadDemo;

	private final SerializableIsolationDemo serializableIsolationDemo;

	public OrderProcessingController(OrderProcessingService orderProcessingService,
			ReadUncommittedDemo readUncommittedDemo,
			ReadCommittedDemo readCommittedDemo,
			RepeatableReadDemo repeatableReadDemo,
			SerializableIsolationDemo serializableIsolationDemo)
	{
		this.orderProcessingService = orderProcessingService;
		this.readUncommittedDemo = readUncommittedDemo;
		this.readCommittedDemo = readCommittedDemo;
		this.repeatableReadDemo = repeatableReadDemo;
		this.serializableIsolationDemo = serializableIsolationDemo;
	}

	/**
	 * API to place an order
	 *
	 * @param order the order details
	 * @return the processed order with updated total price
	 */
	@PostMapping
	public ResponseEntity<?> placeOrder(@RequestBody Order order)
	{
		return ResponseEntity.ok(orderProcessingService.placeAnOrder(order));
	}

	/**
	 * API to test different Isolation types
	 * 
	 * @param productId the product id, e.g. 1 for laptop
	 * @return success message
	 * @throws InterruptedException
	 */
	@GetMapping("/isolation")
	public String testIsolation(@PathVariable int productId) throws InterruptedException
	{
		// readUncommittedDemo.testReadUncommitted(productId);
		// readCommittedDemo.testReadCommitted(productId);
		// repeatableReadDemo.demonstrateRepeatableRead(productId);
		serializableIsolationDemo.testSerializableIsolation(productId);
		return "success";
	}

}
