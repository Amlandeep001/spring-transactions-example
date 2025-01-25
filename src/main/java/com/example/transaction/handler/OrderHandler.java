package com.example.transaction.handler;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.transaction.entity.Order;
import com.example.transaction.repository.OrderRepository;

@Service
public class OrderHandler
{
	private final OrderRepository orderRepository;

	public OrderHandler(OrderRepository orderRepository)
	{
		this.orderRepository = orderRepository;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Order saveOrder(Order order)
	{
		return orderRepository.insert(order);
	}
}
