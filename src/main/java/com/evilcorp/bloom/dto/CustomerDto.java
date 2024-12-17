package com.evilcorp.bloom.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CustomerDto {
  @NotBlank(message = "Name cannot be blank")
  public String name; // first and last name

  @Email(message = "Email must be a valid address")
  @NotBlank(message = "Email cannot be blank")
  public String emailAddress;

  @Pattern(regexp = "^(?:\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4})$", message = "Phone number must be in a valid format")
  public String phoneNumber;
}
