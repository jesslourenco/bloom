package com.evilcorp.bloom.dto;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class FindProductsByCategoryDtoTests {

  private static Validator validator;

  @BeforeAll
  public static void init() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void validDto() {
    FindProductsByCategoryDto dto = new FindProductsByCategoryDto();
    dto.page = 0;
    dto.categoryId = 1;

    Set<ConstraintViolation<FindProductsByCategoryDto>> violations = validator.validate(dto);
    assertTrue(violations.isEmpty());
  }

  @Test
  public void invalidDto_negativePage() {
    FindProductsByCategoryDto dto = new FindProductsByCategoryDto();
    dto.page = -1;
    dto.categoryId = 1;

    Set<ConstraintViolation<FindProductsByCategoryDto>> violations = validator.validate(dto);
    assertEquals(1, violations.size());
    assertTrue(violations.stream()
        .findFirst()
        .map(v -> v.getConstraintDescriptor().getAnnotation().annotationType().equals(Min.class))
        .orElse(false));
  }

  @Test
  public void invalidDto_BlankFields() {
    FindProductsByCategoryDto dto = new FindProductsByCategoryDto();

    Set<ConstraintViolation<FindProductsByCategoryDto>> violations = validator.validate(dto);
    assertEquals(2, violations.size());
    assertTrue(violations.stream()
        .allMatch(v -> v.getConstraintDescriptor().getAnnotation().annotationType().equals(NotNull.class)));
  }

}
