package com.rawat.FinCore.DTO;


import java.time.LocalDateTime;

public class AuditLogDto {
    private Long id;
    private Long userId;
    private String entityName;
    private Long entityId;
    private String action;
    private LocalDateTime timestamp;
    private String details;
}

