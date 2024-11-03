package com.evilcorp.bloom.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class FindProductsByCategoryDto {
  @NotNull
  @Min(0)
  public Integer page;

  @NotNull
  public Integer categoryId;
}
