package com.evilcorp.bloom.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductDto {
  @NotBlank
  @Size(max = 255)
  private String productName;

  @NotBlank
  @Size(max = 4000)
  private String description;

  @Positive
  private Integer category;

  @NotNull
  @NotBlank
  private Integer brand;

  @Size(max = 2048)
  private String imgUrl;

  @NotNull
  @Min(0)
  private Double price;

  @NotNull
  @Min(0)
  private Double cost;

  @Min(0)
  private Integer stockQty;
}
