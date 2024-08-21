package com.evilcorp.bloom.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryDto {

  @NotBlank(message = "Category name cannot be blank")
  public String category;

  public String parentCategory;
}
