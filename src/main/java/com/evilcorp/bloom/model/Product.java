package com.evilcorp.bloom.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("catalog")
public record Product(
    @Id String id,

    String productName,
    String description,
    String category,
    String brand,
    String imgUrl,
    Double price,
    Double cost,
    Integer stockQty,
    String createdAt,
    String updatedAt) {
}
