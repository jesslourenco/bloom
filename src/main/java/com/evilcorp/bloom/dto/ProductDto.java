package com.evilcorp.bloom.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductDto {
  @NotBlank(message = "Product name cannot be blank")
  @Size(max = 255, message = "Name is too long")
  private String name;

  @NotBlank(message = "Product description cannot be blank")
  @Size(max = 4000, message = "Description is too long")
  private String description;

  @Min(0)
  private Integer categoryId;

  @NotNull(message = "Brand id cannot be null")
  private Integer brandId;

  @Size(max = 2048, message = "ImgUrl is too long")
  private String imgUrl;

  @NotNull(message = "Price cannot be null")

  @Min(0)
  private Double price;

  @NotNull(message = "Cost cannot be null")
  @Min(0)
  private Double cost;

  @NotNull(message = "stockQty cannot be null")
  @Min(0)
  private Integer stockQty;

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
