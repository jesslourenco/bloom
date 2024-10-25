package com.evilcorp.bloom.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.evilcorp.bloom.model.Product;

public interface ProductRepo extends CrudRepository<Product, Integer> {
  List<Product> findByNameContaining(String keyword);
}
