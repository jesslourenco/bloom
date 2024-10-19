package com.evilcorp.bloom.repo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.evilcorp.bloom.model.Brand;
import com.evilcorp.bloom.model.Product;

@ActiveProfiles("test")
@SpringBootTest
public class ProductRepoTests {
  @Autowired
  private ProductRepo productRepo;

  @Autowired
  private BrandRepo brandRepo;

  @Test
  public void testFindByProductNameContaining() {
    Brand brand = new Brand("Apple");
    brand.setId(1);
    brandRepo.save(brand);

    Product product = new Product("macbook air", "a cool laptop", null, 1, "", 3000.00, 1500.00, 0);
    productRepo.save(product);

    List<Product> foundProducts = productRepo.findByProductNameContaining("air");

    assertEquals(1, foundProducts.size());
    assertThat(foundProducts.get(0).getProductName().equals(product.getProductName()));
    assertThat(foundProducts.get(0).getDescription().equals(product.getDescription()));
    assertThat(foundProducts.get(0).getCategory() == null);
    assertThat(foundProducts.get(0).getBrand().equals(product.getBrand()));
    assertThat(foundProducts.get(0).getImgUrl().isEmpty());
    assertThat(foundProducts.get(0).getPrice().equals(product.getPrice()));
    assertThat(foundProducts.get(0).getCost().equals(product.getCost()));
    assertThat(foundProducts.get(0).getStockQty().equals(product.getStockQty()));
  }

  @Test
  public void testFindByProductNameContaining_NotFound() {
    List<Product> foundProducts = productRepo.findByProductNameContaining("ipad");

    assertEquals(0, foundProducts.size());
  }
}
