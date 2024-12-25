package com.evilcorp.bloom.repo;

import static org.assertj.core.api.Assertions.assertThat;
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
  public void testFindCustomerByEmail() {
    Customer customer = new Customer("elliot", "alderson", "ea@evilcorp.com", "123-456-7890");
    customerRepo.save(customer);

    Optional<Customer> foundCustomer = customerRepo.findByEmail(customer.getEmail());

    assertThat(foundCustomer).isPresent();
    foundCustomer.ifPresent(c -> {
      assertThat(c.getFirstName()).isEqualTo(customer.getFirstName());
      assertThat(c.getLastName()).isEqualTo(customer.getLastName());
      assertThat(c.getEmail()).isEqualTo(customer.getEmail());
      assertThat(c.getPhone()).isEqualTo(customer.getPhone());
    });
  }

  @Test
  public void testFindCustomerByEmail_Failure() {

    Optional<Customer> foundCustomer = customerRepo.findByEmail("dne@email.com");
    assertThat(foundCustomer.isEmpty());
  }
}
