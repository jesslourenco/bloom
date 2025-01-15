package com.evilcorp.bloom.dto;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.*;

import static org.junit.jupiter.api.Assertions.*;

import static com.evilcorp.bloom.util.RandUtil.generateRandomString;

public class ProductDtoTests {

  private static Validator validator;

  private ProductDto dto;

  @BeforeAll
  static void initValidator() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @BeforeEach
  public void init() {
    this.dto = new ProductDto();
    this.dto.setName("macbook air");
    this.dto.setDescription("a laptop for the casual user");
    this.dto.setBrandId(1);
    this.dto.setPrice(3000.67);
    this.dto.setCost(2000.00);
  }

  @Test
  public void testValidDto() {
    Set<ConstraintViolation<ProductDto>> violations = validator.validate(dto);

    assertTrue(violations.isEmpty());
  }

  @Test
  public void testInvalidDto_LongName() {
    dto.setName(generateRandomString(256));

    Set<ConstraintViolation<ProductDto>> violations = validator.validate(dto);

    assertEquals(1, violations.size());
    assertTrue(violations.stream()
        .findFirst()
        .map(v -> v.getConstraintDescriptor().getAnnotation().annotationType().equals(Size.class))
        .orElse(false));
  }

  @Test
  public void testInvalidDto_BlankFields() {
    ProductDto blankDto = new ProductDto();
    blankDto.setBrandId(1);
    blankDto.setPrice(0.0);
    blankDto.setCost(0.0);

    Set<ConstraintViolation<ProductDto>> violations = validator.validate(blankDto);

    assertEquals(2, violations.size());
    assertTrue(violations.stream()
        .allMatch(v -> v.getConstraintDescriptor().getAnnotation().annotationType().equals(NotBlank.class)));
  }

  @Test
  public void testInvalidDto_NullFields() {
    dto.setBrandId(null);
    dto.setPrice(null);
    dto.setCost(null);

    Set<ConstraintViolation<ProductDto>> violations = validator.validate(dto);

    assertEquals(3, violations.size());
    assertTrue(violations.stream()
        .allMatch(v -> v.getConstraintDescriptor().getAnnotation().annotationType().equals(NotNull.class)));
  }

  @Test
  public void testInvalidDto_LongDescription() {
    dto.setDescription(generateRandomString(4001));

    Set<ConstraintViolation<ProductDto>> violations = validator.validate(dto);

    assertEquals(1, violations.size());
    assertTrue(violations.stream()
        .findFirst()
        .map(v -> v.getConstraintDescriptor().getAnnotation().annotationType().equals(Size.class))
        .orElse(false));
  }

  @Test
  public void testInvalidDto_LongImgUrl() {
    dto.setImgUrl(generateRandomString(2049));

    Set<ConstraintViolation<ProductDto>> violations = validator.validate(dto);

    assertEquals(1, violations.size());
    assertTrue(violations.stream()
        .findFirst()
        .map(v -> v.getConstraintDescriptor().getAnnotation().annotationType().equals(Size.class))
        .orElse(false));
  }

  @Test
  public void testInvalidDto_NegativeDecimals() {
    dto.setPrice(-10.99);
    dto.setCost(-5.00);
    dto.setCategoryId(-5);
    dto.setStockQty(-1);

    Set<ConstraintViolation<ProductDto>> violations = validator.validate(dto);

    assertEquals(4, violations.size());
    assertTrue(violations.stream()
        .allMatch(v -> v.getConstraintDescriptor().getAnnotation().annotationType().equals(Min.class)));
  }
}
