package com.evilcorp.bloom.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class CategoryDto {

  @NotBlank(message = "Category name cannot be blank")
  @Size(max = 255, message = "Category name cannot exceed 255 characters")
  public String category;

  @PositiveOrZero(message = "Parent category id must be an integer: zero or greater")
  public Integer parentCategoryId;
}
