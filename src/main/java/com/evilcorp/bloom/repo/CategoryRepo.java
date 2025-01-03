package com.evilcorp.bloom.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.evilcorp.bloom.model.Category;

@Repository
public interface CategoryRepo extends CrudRepository<Category, Integer> {
  Optional<Category> findByName(String name);
}
