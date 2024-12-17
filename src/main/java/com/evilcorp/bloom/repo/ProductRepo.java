package com.evilcorp.bloom.repo;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.evilcorp.bloom.model.Product;

public interface ProductRepo extends PagingAndSortingRepository<Product, String> {
}
