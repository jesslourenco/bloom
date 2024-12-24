package com.evilcorp.bloom.repo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import com.evilcorp.bloom.model.Customer;

@ActiveProfiles("test")
@SpringBootTest
public class CustomerRepoTests {

  @Autowired
  private CustomerRepo customerRepo;

  @Test
  public void FindCustomerByEmailSuccess() {
    Customer customer = new Customer("elliot", "alderson", "ea@evilcorp.com", "123-456-7890");
    customerRepo.save(customer);

    Optional<Customer> foundCustomer = customerRepo.findByEmail(customer.getEmail());

    Assertions.assertThat(foundCustomer).isPresent();
    foundCustomer.ifPresent(c -> {
      Assertions.assertThat(c.getFirstName()).isEqualTo(customer.getFirstName());
      Assertions.assertThat(c.getLastName()).isEqualTo(customer.getLastName());
      Assertions.assertThat(c.getEmail()).isEqualTo(customer.getEmail());
      Assertions.assertThat(c.getPhone()).isEqualTo(customer.getPhone());
    });
  }
}
