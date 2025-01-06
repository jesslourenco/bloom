package com.evilcorp.bloom.repo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.evilcorp.bloom.model.Brand;
import com.evilcorp.bloom.repo.BrandRepo;

@ActiveProfiles("test")
@SpringBootTest
public class BrandRepoTests {
  @Autowired
  private BrandRepo brandRepo;

  @Test
  public void testExistsByName() {
    Brand brand = new Brand("Apple");
    brandRepo.save(brand);

    assertTrue(brandRepo.existsByName(brand.getName()));
  }

  @Test
  public void testExistsByName_NotFound() {
    String name = "Apple";

    assertFalse(brandRepo.existsByName(name));
  }
}
