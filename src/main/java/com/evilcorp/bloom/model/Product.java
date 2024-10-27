package com.evilcorp.bloom.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("catalog")
public class Product {
  @Id
  private String id;

  @Column("product_name")
  private String name;

  private String description;
  private Integer categoryId;
  private Integer brandId;
  private String imgUrl;

  private Double price;
  private Double cost;
  private Integer stockQty;

  public Product(String name, String description, Integer categoryId, Integer brandId,
      String imgUrl, Double price, Double cost, Integer stockQty) {
    this.name = name;
    this.description = description;
    this.categoryId = categoryId;
    this.brandId = brandId;
    this.imgUrl = imgUrl;
    this.price = price;
    this.cost = cost;
    this.stockQty = stockQty;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Integer categoryId) {
    this.categoryId = categoryId;
  }

  public Integer getBrandId() {
    return brandId;
  }

  public void setBrandId(Integer brandId) {
    this.brandId = brandId;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Double getCost() {
    return cost;
  }

  public void setCost(Double cost) {
    this.cost = cost;
  }

  public Integer getStockQty() {
    return stockQty;
  }

  public void setStockQty(Integer stockQty) {
    this.stockQty = stockQty;
  }
}
