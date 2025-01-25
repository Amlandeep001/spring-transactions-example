package com.example.transaction.repository;

import com.example.transaction.entity.AuditLog;
import com.example.transaction.repository.base.InsertUpdateRepository;

public interface AuditLogRepository extends InsertUpdateRepository<AuditLog, Long>
{
}
