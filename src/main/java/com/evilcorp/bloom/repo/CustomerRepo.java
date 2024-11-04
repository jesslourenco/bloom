package com.evilcorp.bloom.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.evilcorp.bloom.model.Customer;

import java.util.Optional;

@Repository
public interface CustomerRepo extends CrudRepository<Customer, String> {
  Optional<Customer> findByEmail(String email);

  boolean existsByEmail(String email);
}
