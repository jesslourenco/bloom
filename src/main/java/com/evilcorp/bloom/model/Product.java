package com.evilcorp.bloom.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("catalog")
public class Product {
  @Id
  private String id;

  private String name;
  private String description;
  private String category;
  private String brand;
  private String imgUrl;

  private Double price;
  private Double cost;
  private Integer stockQty;

  private String createdAt;
  private String UpdatedAt;
}
