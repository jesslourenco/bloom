package com.evilcorp.bloom.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class ProductDto {

  @NotBlank(message = "Product name is required.")
  @Size(max = 100, message = "Product name must not exceed 100 characters.")
  private String productName;

  @Size(max = 500, message = "Description must not exceed 500 characters.")
  private String description;

  @NotBlank(message = "Category is required.")
  private Integer categoryId;

  @NotBlank(message = "Brand is required.")
  @Size(max = 50, message = "Brand must not exceed 50 characters.")
  private String brand;

  @Pattern(regexp = "^(https?://.*|)$", message = "Image URL must be a valid URL starting with http or https.")
  private String imgUrl;

  @NotNull(message = "Price is required.")
  @Positive(message = "Price must be greater than 0.")
  private Double price;

  @NotNull(message = "Cost is required.")
  @PositiveOrZero(message = "Cost must be greater than or equal to 0.")
  private Double cost;

  @NotNull(message = "Stock quantity is required.")
  @Min(value = 0, message = "Stock quantity must be 0 or greater.")
  private Integer stockQty;

  public ProductDto(String name, String description, Integer categoryId, String brand,
      String imgUrl, Double price, Double cost, Integer stockQty) {

    this.productName = name;
    this.description = description;
    this.categoryId = categoryId;
    this.brand = brand;
    this.imgUrl = imgUrl;
    this.price = price;
    this.cost = cost;
    this.stockQty = stockQty;
  }

  public String getName() {
    return productName;
  }

  public void setName(String name) {
    this.productName = name;
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

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
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
