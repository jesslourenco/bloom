package com.evilcorp.bloom.dto;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.evilcorp.bloom.util.RandUtil;

import static org.junit.jupiter.api.Assertions.*;

public class BrandDtoTests {
  private static Validator validator;

  @BeforeAll
  static void init() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  public void testValidDto() {
    BrandDto dto = new BrandDto();
    dto.name = "Apple";

    Set<ConstraintViolation<BrandDto>> violations = validator.validate(dto);

    assertTrue(violations.isEmpty());
  }

  public void testInvalidDto_LongName() {
    BrandDto dto = new BrandDto();
    dto.name = RandUtil.generateRandomString(51);

    Set<ConstraintViolation<BrandDto>> violations = validator.validate(dto);

    assertEquals(1, violations.size());
    assertTrue(violations.stream()
        .findFirst()
        .map(v -> v.getConstraintDescriptor().getAnnotation().annotationType().equals(Size.class))
        .orElse(false));
  }

  public void testInvalidDto_BlankName() {
    BrandDto dto = new BrandDto();

    Set<ConstraintViolation<BrandDto>> violations = validator.validate(dto);

    assertEquals(1, violations.size());
    assertTrue(violations.stream()
        .findFirst()
        .map(v -> v.getConstraintDescriptor().getAnnotation().annotationType().equals(NotBlank.class))
        .orElse(false));
  }
}
