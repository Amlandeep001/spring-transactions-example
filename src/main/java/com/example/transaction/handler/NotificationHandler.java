package com.example.transaction.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.transaction.entity.Order;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class NotificationHandler
{
	@Transactional(propagation = Propagation.NEVER)
	public void sendOrderConfirmationNotification(Order order)
	{
		// Send an email notification to the customer
		System.out.println(order.getId() + " Order placed successfully");
		log.info("Order placed successfully with id {}", order.getId());
	}
}
