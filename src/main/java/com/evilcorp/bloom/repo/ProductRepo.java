package com.evilcorp.bloom.repo;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import com.evilcorp.bloom.model.Product;

public interface ProductRepo extends CrudRepository<Product, Integer> {
  List<Product> findByNameContaining(String keyword);

  @Query("SELECT * FROM catalog WHERE category_id = :category_id ORDER BY category_id ASC LIMIT :limit OFFSET :offset")
  List<Product> findAllPaged(Integer category_id, int limit, int offset);

  @Query("SELECT COUNT(*) FROM catalog WHERE category_id = :category_id")
  long countByCategory(Integer category_id);
}
