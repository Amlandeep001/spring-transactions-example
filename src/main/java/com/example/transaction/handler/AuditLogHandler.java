package com.example.transaction.handler;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.transaction.entity.AuditLog;
import com.example.transaction.entity.Order;
import com.example.transaction.repository.AuditLogRepository;

import lombok.val;

@Component
public class AuditLogHandler
{
	private final AuditLogRepository auditLogRepository;

	public AuditLogHandler(AuditLogRepository auditLogRepository)
	{
		this.auditLogRepository = auditLogRepository;
	}

	// Log audit details (runs in an independent transaction)
	// @Transactional(propagation = Propagation.REQUIRES_NEW)
	public void logAuditDetails(Order order, String action)
	{
		val auditLog = AuditLog.builder()
				.orderId(Long.valueOf(order.getId()))
				.action(action)
				.timestamp(LocalDateTime.now())
				.build();

		// Save the audit log
		auditLogRepository.insert(auditLog);
	}
}
