package com.evilcorp.bloom.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.evilcorp.bloom.model.Category;

@Repository
public interface CategoryRepo extends CrudRepository<Category, UUID> {
  Optional<Category> findByName(String name);

  boolean existsByName(String name);

  @Modifying
  @Query("DELETE FROM category WHERE name = :name")
  void deleteByName(@Param("name") String name);
}
