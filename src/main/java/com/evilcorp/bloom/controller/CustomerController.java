package com.evilcorp.bloom.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evilcorp.bloom.service.CustomerService;

import jakarta.validation.Valid;

import com.evilcorp.bloom.model.Customer;
import com.evilcorp.bloom.dto.CustomerDto;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping("/{id}")
  public Customer getCustomerById(@PathVariable String id) {
    return customerService.getOneById(id);
  }

  @GetMapping
  public Iterable<Customer> getAllCustomers() {
    return customerService.getAll();
  }

  @PostMapping("/find-email")
  public Customer getCustomerByEmail(@RequestBody String email) {
    return customerService.getOneByEmail(email);
  }

  @PostMapping
  public void createCustomer(@RequestBody @Valid CustomerDto dto) {
    customerService.add(dto);
  }
}
