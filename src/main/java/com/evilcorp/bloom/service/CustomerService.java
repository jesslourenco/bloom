package com.evilcorp.bloom.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.evilcorp.bloom.repo.CustomerRepo;
import com.evilcorp.bloom.dto.CustomerDto;
import com.evilcorp.bloom.exception.NotFoundException;
import com.evilcorp.bloom.model.Customer;

@Service
public class CustomerService {
  public final CustomerRepo customerRepo;

  public CustomerService(CustomerRepo customerRepo) {
    this.customerRepo = customerRepo;
  }

  public void add(CustomerDto dto) {
    if (customerRepo.existsByEmail(dto.getEmailAddress())) {
      throw new DataIntegrityViolationException("email already registered");
    }

    String[] nameParts = dto.getName().trim().split("\\s+", 2);
    String firstName = nameParts[0];
    String lastName = nameParts[1];

    String phone = dto.getPhoneNumber().replaceAll("[^0-9]", "");

    Customer customer = new Customer(firstName, lastName, dto.getEmailAddress(), phone);

    customerRepo.save(customer);
  }

  public Iterable<Customer> getAll() {
    return customerRepo.findAll();
  }

  public Customer getOneById(String id) {
    return customerRepo.findById(id).orElseThrow(
        () -> new NotFoundException(String.format("Customer with id %s not found", id)));
  }

  public Customer getOneByEmail(String email) {
    return customerRepo.findByEmail(email).orElseThrow(
        () -> new NotFoundException(String.format("Customer with email %s not found", email)));
  }

  public void deleteById(String id) {
    if (!customerRepo.existsById(id)) {
      throw new NotFoundException(String.format("Customer with id %s not found", id));
    }
    customerRepo.deleteById(id);
  }
}
