package com.evilcorp.bloom.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BrandDto {
  @Size(max = 50, message = "Brand name is too long.")
  @NotBlank(message = "Brand name cannot be blank.")
  public String name;
}
