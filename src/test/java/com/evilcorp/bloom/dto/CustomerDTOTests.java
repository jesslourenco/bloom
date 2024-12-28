package com.evilcorp.bloom.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CustomerDTOTests {

  private static Validator validator;

  @BeforeAll
  static void init() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void testValidDTO() {
    CustomerDto dto = new CustomerDto();
    dto.name = "John Doe";
    dto.emailAddress = "john.doe@email.com";
    dto.phoneNumber = "123-456-7890";

    Set<ConstraintViolation<CustomerDto>> violations = validator.validate(dto);
    assertTrue(violations.isEmpty());
  }

  @Test
  public void testValidDTO_ValidPhones() {
    CustomerDto dto = new CustomerDto();
    dto.name = "John Doe";
    dto.emailAddress = "john.doe@email.com";

    String[] validPhones = {
        "123-456-7890",
        "123 456 7890",
        "(123) 456 7890",
        "123.456.7890",
        "1234567890"
    };

    for (String phone : validPhones) {
      dto.phoneNumber = phone;
      Set<ConstraintViolation<CustomerDto>> violations = validator.validate(dto);

      assertTrue(violations.isEmpty());
    }
  }

  @Test
  public void testInvalidDTO_Null() {
    CustomerDto dto = new CustomerDto();

    Set<ConstraintViolation<CustomerDto>> violations = validator.validate(dto);

    assertFalse(violations.isEmpty());
    assertEquals(2, violations.size());

    assertTrue(violations.stream()
        .allMatch(v -> v.getConstraintDescriptor().getAnnotation().annotationType().equals(NotBlank.class)));
  }

  @Test
  public void testInvalidDTO_BadFormattedEmail() {
    CustomerDto dto = new CustomerDto();
    dto.name = "John Doe";

    String[] invalidEmails = {
        "fake",
        "fake@",
        "fake@email",
        "fake@email.",
        "fake.@email.com"
    };

    for (String email : invalidEmails) {
      dto.emailAddress = email;
      Set<ConstraintViolation<CustomerDto>> violations = validator.validate(dto);

      assertFalse(violations.isEmpty());
      assertEquals(1, violations.size());
      assertTrue(violations.stream()
          .allMatch(v -> v.getConstraintDescriptor().getAnnotation().annotationType().equals(Email.class)));
    }
  }

  @Test
  public void testInvalidDTO_BadFormattedPhone() {
    CustomerDto dto = new CustomerDto();
    dto.name = "John Doe";
    dto.emailAddress = "john.doe@email.com";

    String[] invalidPhones = {
        "123",
        "123-456-789",
        "55 123-456-7890",
        "123--456-7890",
        "123&456&7890"
    };

    for (String phone : invalidPhones) {
      dto.phoneNumber = phone;
      Set<ConstraintViolation<CustomerDto>> violations = validator.validate(dto);
      assertFalse(violations.isEmpty());
      assertEquals(1, violations.size());
      assertTrue(violations.stream()
          .allMatch(v -> v.getConstraintDescriptor().getAnnotation().annotationType().equals(Pattern.class)));
    }
  }
}
