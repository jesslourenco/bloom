package com.evilcorp.bloom.service;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.evilcorp.bloom.dto.ProductDto;
import com.evilcorp.bloom.dto.ProductMapper;
import com.evilcorp.bloom.model.Product;
import com.evilcorp.bloom.repo.ProductRepo;
import com.evilcorp.bloom.exception.NotFoundException;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {
  @Mock
  private ProductRepo productRepo;

  @InjectMocks
  private ProductService productService;

  @Spy
  private ProductMapper productMapper;

  private Product product;
  private ProductDto dto;

  @BeforeEach
  public void init() {
    this.dto = new ProductDto();
    this.dto.setName("macbook air");
    this.dto.setDescription("a laptop for the casual user");
    this.dto.setCategoryId(null);
    this.dto.setBrandId(1);
    this.dto.setPrice(3000.67);
    this.dto.setCost(2000.00);
    this.dto.setStockQty(0);

    product = new Product("Macbook Air",
        "a laptop for the casual user",
        null,
        1,
        null,
        3000.67,
        2000.00,
        0);
  }

  @Test
  public void testAdd() {
    when(productMapper.toProduct(dto)).thenReturn(product);

    productService.add(dto);

    verify(productRepo, times(1))
        .save(argThat(p -> p.getName().equals(product.getName()) &&
            p.getDescription().equals(product.getDescription()) &&
            p.getCategoryId() == null &&
            p.getBrandId().equals(product.getBrandId()) &&
            p.getPrice().equals(product.getPrice()) &&
            p.getCost().equals(product.getCost()) &&
            p.getStockQty().equals(product.getStockQty())));
  }

  @Test
  public void testAdd_InvalidBrandId() {
    when(productMapper.toProduct(dto)).thenReturn(product);
    when(productRepo.save(any(Product.class)))
        .thenThrow(new DataIntegrityViolationException("constraint [23503]: foreign key issue"));

    assertThatThrownBy(() -> productService.add(dto))
        .isInstanceOf(NotFoundException.class);
  }

}
