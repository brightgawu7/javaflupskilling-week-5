package com.qwammy.javaflupskilling_week_5.dtos.product.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadProductDto {
    private UUID id;
    private String name;
    private String description;
    private double price;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
