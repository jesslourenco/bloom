package com.evilcorp.bloom.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotBlank;

public class CategoryDtoTests {

  private static Validator validator;

  @BeforeAll
  static void init() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void testValidDTO() {
    CategoryDto dto = new CategoryDto();
    dto.category = "Eletronics";
    dto.parentCategory = "";

    Set<ConstraintViolation<CategoryDto>> violations = validator.validate(dto);
    assertTrue(violations.isEmpty());
  }

  @Test
  public void testInvalidDTO_BlankCategory() {
    CategoryDto dto = new CategoryDto();

    Set<ConstraintViolation<CategoryDto>> violations = validator.validate(dto);

    assertEquals(1, violations.size());
    assertTrue(violations.stream()
        .findFirst()
        .map(v -> v.getConstraintDescriptor().getAnnotation().annotationType().equals(NotBlank.class))
        .orElse(false));
  }
}
