package com.rawat.FinCore.Entities;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 50)
    private String entityName;   // e.g., "Account", "Loan"

    @Column(nullable = false)
    private Long entityId;

    @Column(nullable = false, length = 50)
    private String action;       // e.g., "CREATE", "UPDATE", "DELETE"

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(columnDefinition = "TEXT")
    private String details;

    // getters and setters
}
