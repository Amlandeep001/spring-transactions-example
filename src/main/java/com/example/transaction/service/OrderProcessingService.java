package com.example.transaction.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.transaction.entity.Order;
import com.example.transaction.entity.Product;
import com.example.transaction.handler.AuditLogHandler;
import com.example.transaction.handler.InventoryHandler;
import com.example.transaction.handler.NotificationHandler;
import com.example.transaction.handler.OrderHandler;
import com.example.transaction.handler.PaymentValidatorHandler;
import com.example.transaction.handler.ProductRecommendationHandler;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OrderProcessingService
{
	private final OrderHandler orderHandler;

	private final InventoryHandler inventoryHandler;

	private final AuditLogHandler auditLogHandler;

	private final PaymentValidatorHandler paymentValidatorHandler;

	private final NotificationHandler notificationHandler;

	private final ProductRecommendationHandler recommendationHandler;

	public OrderProcessingService(OrderHandler orderHandler,
			InventoryHandler inventoryHandler,
			AuditLogHandler auditLogHandler,
			PaymentValidatorHandler paymentValidatorHandler,
			NotificationHandler notificationHandler,
			ProductRecommendationHandler recommendationHandler)
	{
		this.orderHandler = orderHandler;
		this.inventoryHandler = inventoryHandler;
		this.auditLogHandler = auditLogHandler;
		this.paymentValidatorHandler = paymentValidatorHandler;
		this.notificationHandler = notificationHandler;
		this.recommendationHandler = recommendationHandler;
	}

	// REQUIRED : Joins in an existing transaction or creates a new one if not exist
	// REQUIRES_NEW : Always creates a new transaction , suspending if any existing transaction
	// MANDATORY : Requires an existing transaction and if nothing is found, it will throw an exception
	// NEVER : Ensures the method will run without transaction , throw an exception if found any
	// NOT_SUPPORTED : Executes method without transaction, suspending any active transaction
	// SUPPORTS : Supports if there is any active transaction , if not executes without transaction
	// NESTED : Executes within a nested transaction, allowing nested transaction
	// to rollback independently if there is any exception without impacting outer transaction

	// outer transaction
	@Transactional(propagation = Propagation.REQUIRED)
	public Order placeAnOrder(Order order)
	{
		// get product inventory
		Product product = inventoryHandler.getProduct(order.getProductId());

		// validate stock availability <(5)
		validateStockAvailability(order, product);

		// update total price in order entity
		order.setTotalPrice(order.getQuantity() * product.getPrice());

		Order saveOrder = null;
		try
		{
			// save order
			saveOrder = orderHandler.saveOrder(order);

			// update stock in inventory
			updateInventoryStock(order, product);
			auditLogHandler.logAuditDetails(order, "order placement succeeded");
		}
		catch(Exception ex)
		{
			auditLogHandler.logAuditDetails(order, "order placement failed");
		}

		// retries 3
		// notificationHandler.sendOrderConfirmationNotification(order);

		// paymentValidatorHandler.validatePayment(order);

		// recommendationHandler.getRecommendations();

		// getCustomerDetails();

		return saveOrder;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void getCustomerDetails()
	{
		System.out.println("Customer details fetched !!!!!");
		log.info("Customer details fetched !!!!!");
	}

	// Call this method after placeAnOrder is successfully completed
	public void processOrder(Order order)
	{
		// Step 1: Place the order
		Order savedOrder = placeAnOrder(order);

		// Step 2: Send notification (non-transactional)
		notificationHandler.sendOrderConfirmationNotification(order);
	}

	private static void validateStockAvailability(Order order, Product product)
	{
		if(order.getQuantity() > product.getStockQuantity())
		{
			throw new RuntimeException("Insufficient stock !");
		}
	}

	private void updateInventoryStock(Order order, Product product)
	{
		int availableStock = product.getStockQuantity() - order.getQuantity();
		product.setStockQuantity(availableStock);
		inventoryHandler.updateProductDetails(product);
	}

}
