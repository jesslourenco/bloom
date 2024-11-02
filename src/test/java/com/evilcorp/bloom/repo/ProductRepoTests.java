package com.evilcorp.bloom.repo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.evilcorp.bloom.model.Brand;
import com.evilcorp.bloom.model.Category;
import com.evilcorp.bloom.model.Product;

@ActiveProfiles("test")
@SpringBootTest
public class ProductRepoTests {
  @Autowired
  private ProductRepo productRepo;

  @Autowired
  private BrandRepo brandRepo;

  @Autowired
  private CategoryRepo categoryRepo;

  @AfterAll
  static void cleanUp(@Autowired JdbcTemplate jdbcTemplate) {
    jdbcTemplate.execute("TRUNCATE TABLE catalog, brands, category RESTART IDENTITY CASCADE");
  }

  @Test
  public void testFindByNameContaining() {
    Brand brand = brandRepo.save(new Brand("Playstation"));

    Product product = new Product("joystick dualshock 2",
        "console joystick",
        null,
        brand.getId(),
        "",
        3000.00,
        1500.00,
        0);

    productRepo.save(product);

    List<Product> foundProducts = productRepo.findByNameContaining("dualshock");

    assertEquals(1, foundProducts.size());
    assertThat(foundProducts.get(0).getName().equals(product.getName()));
    assertThat(foundProducts.get(0).getDescription().equals(product.getDescription()));
    assertThat(foundProducts.get(0).getCategoryId() == null);
    assertThat(foundProducts.get(0).getBrandId().equals(product.getBrandId()));
    assertThat(foundProducts.get(0).getImgUrl().isEmpty());
    assertThat(foundProducts.get(0).getPrice().equals(product.getPrice()));
    assertThat(foundProducts.get(0).getCost().equals(product.getCost()));
    assertThat(foundProducts.get(0).getStockQty().equals(product.getStockQty()));
  }

  @Test
  public void testFindByNameContaining_NotFound() {
    List<Product> foundProducts = productRepo.findByNameContaining("ipad");

    assertEquals(0, foundProducts.size());
  }

  @Test
  public void testFindAllPaged() {
    Integer categoryId = categoryRepo.save(new Category("Furniture", null)).getId();
    Integer brandId = brandRepo.save(new Brand("brandco")).getId();

    Product chair = productRepo
        .save(new Product("chair", "a chair made of wood", categoryId, brandId, "", 500.00, 50.00, 10));

    Integer page = 0;
    Integer pageSize = 10;

    List<Product> result = productRepo.findAllPaged(categoryId, pageSize, pageSize * page);

    result.stream().forEach(p -> System.out.println(p.getName()));
    assertEquals(1, result.size());
    assertThat(result.contains(chair));
  }

  @Test
  public void testCountByCategory() {
    Integer categoryId = categoryRepo.save(new Category("Home", null)).getId();
    Integer brandId = brandRepo.save(new Brand("HomeCo")).getId();

    productRepo.save(new Product("couch", "a comfy couch", categoryId, brandId, "", 500.00, 50.00, 10));

    productRepo.save(
        new Product("another couch", "another comfy couch, but different", categoryId, brandId, "", 500.00, 50.00, 10));

    long count = productRepo.countByCategory(categoryId);
    assertEquals(2, count);
  }
}
