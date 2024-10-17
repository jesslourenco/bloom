package com.evilcorp.bloom.dto;

import java.util.Set;

import org.apache.tomcat.util.bcel.classfile.Constant;
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
    this.dto.productName = "macbook air";
    this.dto.description = "a laptop for the casual user";
    this.dto.brand = 1;
    this.dto.price = 3000.67;
    this.dto.cost = 2000.00;
  }

  @Test
  public void testValidDto() {
    Set<ConstraintViolation<ProductDto>> violations = validator.validate(dto);

    assertTrue(violations.isEmpty());
  }

  @Test
  public void testInvalidDto_LongName() {
    dto.productName = generateRandomString(256);

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
    blankDto.brand = 1;
    blankDto.price = 0.0;
    blankDto.cost = 0.0;

    Set<ConstraintViolation<ProductDto>> violations = validator.validate(blankDto);

    assertEquals(2, violations.size());
    assertTrue(violations.stream()
        .allMatch(v -> v.getConstraintDescriptor().getAnnotation().annotationType().equals(NotBlank.class)));
  }

  @Test
  public void testInvalidDto_NullFields() {
    dto.brand = null;
    dto.price = null;
    dto.cost = null;

    Set<ConstraintViolation<ProductDto>> violations = validator.validate(dto);

    assertEquals(3, violations.size());
    assertTrue(violations.stream()
        .allMatch(v -> v.getConstraintDescriptor().getAnnotation().annotationType().equals(NotNull.class)));
  }

  @Test
  public void testInvalidDto_LongDescription() {
    dto.description = generateRandomString(4001);

    Set<ConstraintViolation<ProductDto>> violations = validator.validate(dto);

    assertEquals(1, violations.size());
    assertTrue(violations.stream()
        .findFirst()
        .map(v -> v.getConstraintDescriptor().getAnnotation().annotationType().equals(Size.class))
        .orElse(false));
  }

  @Test
  public void testInvalidDto_LongImgUrl() {
    dto.imgUrl = generateRandomString(2049);

    Set<ConstraintViolation<ProductDto>> violations = validator.validate(dto);

    assertEquals(1, violations.size());
    assertTrue(violations.stream()
        .findFirst()
        .map(v -> v.getConstraintDescriptor().getAnnotation().annotationType().equals(Size.class))
        .orElse(false));
  }

  @Test
  public void testInvalidDto_NegativeDecimals() {
    dto.price = -10.99;
    dto.cost = -5.00;
    dto.category = -5;
    dto.stockQty = -1;

    Set<ConstraintViolation<ProductDto>> violations = validator.validate(dto);

    assertEquals(4, violations.size());
    assertTrue(violations.stream()
        .allMatch(v -> v.getConstraintDescriptor().getAnnotation().annotationType().equals(Min.class)));
  }
}
