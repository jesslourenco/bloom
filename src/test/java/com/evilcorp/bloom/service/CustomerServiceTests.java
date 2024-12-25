package com.evilcorp.bloom.service;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.evilcorp.bloom.model.Customer;
import com.evilcorp.bloom.repo.CustomerRepo;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTests {

  @Mock
  private CustomerRepo customerRepo;

  @InjectMocks
  private CustomerService customerService;

  @Test
  public void testGetAllCustomers() {
    Iterable<Customer> mockCustomers = Arrays.asList(
        new Customer("Jane", "Doe", "jane.doe@email.com", "1234567890"),
        new Customer("John", "Doe", "john.doe@email.com", "1234567890"));

    when(customerRepo.findAll()).thenReturn(mockCustomers);
    Iterable<Customer> customers = customerService.getAll();

    verify(customerRepo, times(1)).findAll();
    assertThat(customers).hasSize(2);
    assertThat(customers).containsExactlyInAnyOrderElementsOf(mockCustomers);
  }

  @Test
  public void testGetOneById() {
    Customer mockCustomer = new Customer("Jane", "Doe", "jane.doe@email.com", "1234567890");
    UUID id = UUID.randomUUID();
    mockCustomer.setId(id);

    when(customerRepo.findById(id.toString())).thenReturn(Optional.of(mockCustomer));
    Customer customer = customerService.getOneById(id.toString());

    verify(customerRepo, times(1)).findById(id.toString());
    assertThat(customer).isNotNull();
    assertThat(customer.getId()).isEqualTo(id);
  }

  @Test
  public void testGetOneById_NotFound() {
    UUID id = UUID.randomUUID();

    when(customerRepo.findById(id.toString())).thenReturn(Optional.empty());

    assertThatThrownBy(() -> customerService.getOneById(id.toString()))
        .isInstanceOf(com.evilcorp.bloom.exception.NotFoundException.class);

    verify(customerRepo, times(1)).findById(id.toString());
  }

  @Test
  public void testGetOneByEmail() {
    Customer mockCustomer = new Customer("Jane", "Doe", "jane.doe@email.com", "123-456-7890");

    when(customerRepo.findByEmail(mockCustomer.getEmail())).thenReturn(Optional.of(mockCustomer));
    Customer customer = customerService.getOneByEmail(mockCustomer.getEmail());

    verify(customerRepo, times(1)).findByEmail(mockCustomer.getEmail());
    assertThat(customer).isNotNull();
    assertThat(customer.getEmail()).isEqualTo(mockCustomer.getEmail());
  }

  @Test
  public void testGetOneByEmail_NotFound() {
    String email = "fake@email.com";

    when(customerRepo.findByEmail(email)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> customerService.getOneByEmail(email))
        .isInstanceOf(com.evilcorp.bloom.exception.NotFoundException.class);

    verify(customerRepo, times(1)).findByEmail(email);
  }

  @Test
  public void testDeleteById() {
    List<Customer> mockCustomers = new ArrayList<>(Arrays.asList(
        new Customer("Jane", "Doe", "jane.doe@email.com", "1234567890"),
        new Customer("John", "Doe", "john.doe@email.com", "1234567890")));

    mockCustomers.get(0).setId(UUID.randomUUID());
    UUID id = UUID.randomUUID();
    mockCustomers.get(1).setId(id);

    when(customerRepo.existsById(id.toString())).thenReturn(true);
    when(customerRepo.findAll()).thenReturn(mockCustomers);

    doAnswer(invokation -> {
      mockCustomers.removeIf(customer -> customer.getId().equals(id));
      return null;
    }).when(customerRepo).deleteById(id.toString());

    customerService.deleteById(id.toString());

    verify(customerRepo, times(1)).deleteById(id.toString());

    Iterable<Customer> customers = customerRepo.findAll();
    assertThat(customers).hasSize(1);
    assertThat(customers).noneMatch(customer -> customer.getId().equals(id));
  }

  @Test
  public void testDeleteById_NotFound() {
    UUID id = UUID.randomUUID();

    when(customerRepo.existsById(id.toString())).thenReturn(false);

    assertThatThrownBy(() -> customerService.deleteById(id.toString()))
        .isInstanceOf(com.evilcorp.bloom.exception.NotFoundException.class);

    verify(customerRepo, times(1)).existsById(id.toString());
    verify(customerRepo, times(0)).deleteById(id.toString());
  }

  // test for add()
  // error - duplicate email
}
