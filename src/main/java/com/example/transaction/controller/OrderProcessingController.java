package com.example.transaction.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.transaction.entity.Order;
import com.example.transaction.service.OrderProcessingService;

@RestController
@RequestMapping("/api/orders")
public class OrderProcessingController
{
	private final OrderProcessingService orderProcessingService;

	public OrderProcessingController(OrderProcessingService orderProcessingService)
	{
		this.orderProcessingService = orderProcessingService;
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

}
