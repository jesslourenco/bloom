package com.evilcorp.bloom.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class FindProductsByCategoryDto {
  @NotNull
  @Positive
  public Integer page;

  @NotNull
  public Integer categoryId;
}
