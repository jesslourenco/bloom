package com.evilcorp.bloom.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.evilcorp.bloom.model.Brand;

@Repository
public interface BrandRepo extends CrudRepository<Brand, Integer> {
}
