package com.evilcorp.bloom.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductDto {
  @NotBlank
  @Size(max = 255)
  public String productName;

  @NotBlank
  @Size(max = 4000)
  public String description;

  @Min(0)
  public Integer category;

  @NotNull
  public Integer brand;

  @Size(max = 2048)
  public String imgUrl;

  @NotNull
  @Min(0)
  public Double price;

  @NotNull
  @Min(0)
  public Double cost;

  @Min(0)
  public Integer stockQty;
}
