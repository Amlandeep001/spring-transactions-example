package com.example.transaction.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class AuditLog
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	Long orderId; // The order associated with the log

	String action; // Action taken (e.g., "Order Placed", "Payment Failed")

	LocalDateTime timestamp; // Timestamp of the action

	// Default constructor
	public AuditLog()
	{
		this.timestamp = LocalDateTime.now(); // Default timestamp is the current time
	}
}
