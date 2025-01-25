package com.example.transaction.handler;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.transaction.entity.AuditLog;
import com.example.transaction.entity.Order;
import com.example.transaction.repository.AuditLogRepository;

import lombok.val;

@Service
public class PaymentValidatorHandler
{
	private final AuditLogRepository auditLogRepository;

	public PaymentValidatorHandler(AuditLogRepository auditLogRepository)
	{
		this.auditLogRepository = auditLogRepository;
	}

	@Transactional(propagation = Propagation.MANDATORY)
	public void validatePayment(Order order)
	{
		// Assume payment processing happens here
		boolean paymentSuccessful = false;

		// If payment is unsuccessful, we log the payment failure in the mandatory transaction
		if(!paymentSuccessful)
		{
			val paymentFailureLog = AuditLog.builder()
					.orderId(Long.valueOf(order.getId()))
					.action("Payment Failed for Order")
					.timestamp(LocalDateTime.now())
					.build();

			/*if(order.getTotalPrice() > 1000)
			{
				throw new RuntimeException("Error in payment validator");
			}*/
			// Save the payment failure log
			auditLogRepository.insert(paymentFailureLog);
		}
	}
}
