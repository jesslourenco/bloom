package com.evilcorp.bloom.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.evilcorp.bloom.model.Product;

@SpringBootTest(classes = { ProductMapperImpl.class })
public class ProductMapperTests {

  @Autowired
  private ProductMapper productMapper;

  @Test
  public void testToProduct() {
    ProductDto dto = new ProductDto();
    dto.setName("macbook air");
    dto.setDescription("a laptop for the casual user");
    dto.setBrandId(1);
    dto.setPrice(3000.67);
    dto.setCost(2000.00);
    dto.setStockQty(0);

    Product parsedProduct = productMapper.toProduct(dto);

    assertTrue(parsedProduct.getName().equals(dto.getName()));
    assertTrue(parsedProduct.getDescription().equals(dto.getDescription()));
    assertTrue(parsedProduct.getBrandId().equals(dto.getBrandId()));
    assertTrue(parsedProduct.getPrice().equals(dto.getPrice()));
    assertTrue(parsedProduct.getCost().equals(dto.getCost()));
    assertTrue(parsedProduct.getStockQty().equals(dto.getStockQty()));

    assertNull(parsedProduct.getImgUrl());
    assertNull(parsedProduct.getId());
  }

}
