package com.evilcorp.bloom.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.evilcorp.bloom.util.RandUtil;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

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
    dto.category = "Electronics";
    dto.parentCategoryId = null;

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

  @Test
  public void testInvalidDTO_LongName() {
    CategoryDto dto = new CategoryDto();
    dto.category = RandUtil.generateRandomString(256);
    Set<ConstraintViolation<CategoryDto>> violations = validator.validate(dto);

    assertEquals(1, violations.size());
    assertTrue(violations.stream()
        .findFirst()
        .map(v -> v.getConstraintDescriptor().getAnnotation().annotationType().equals(Size.class))
        .orElse(false));
  }
}
