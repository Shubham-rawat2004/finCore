package com.rawat.FinCore.Repository;

import java.time.LocalDateTime;
import java.util.List;

import com.rawat.FinCore.Entities.AuditLog;
import com.rawat.FinCore.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByUser(User user);

    List<AuditLog> findByEntityNameAndEntityId(String entityName, Long entityId);

    List<AuditLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
