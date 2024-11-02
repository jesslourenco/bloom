package com.evilcorp.bloom.repo;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.evilcorp.bloom.model.Category;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
public class CategoryRepoTests {
  @Autowired
  private CategoryRepo categoryRepo;

  @AfterAll
  static void cleanUp(@Autowired JdbcTemplate jdbcTemplate) {
    jdbcTemplate.execute("TRUNCATE TABLE category RESTART IDENTITY CASCADE");
  }

  @Test
  public void testFindByName() {
    Category category = new Category("Eletronics", null);
    categoryRepo.save(category);

    Optional<Category> foundCategory = categoryRepo.findByName(category.getName());
    assertThat(foundCategory).isPresent();

    foundCategory.ifPresent(c -> {
      assertThat(c.getName().equals(category.getName()));
      assertThat(c.getParentCategoryId()).isNull();
    });
  }

  @Test
  public void testFindByName_NotFound() {
    String name = "Eletronics";
    Optional<Category> foundCategory = categoryRepo.findByName(name);
    assertThat(foundCategory).isNotPresent();
  }
}
