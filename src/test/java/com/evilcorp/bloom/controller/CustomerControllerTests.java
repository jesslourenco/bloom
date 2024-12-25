package com.evilcorp.bloom.controller;

import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.evilcorp.bloom.dto.CustomerDto;
import com.evilcorp.bloom.model.Customer;
import com.evilcorp.bloom.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CustomerService customerService;

  @Autowired
  private ObjectMapper objectMapper;

  private Customer customer;
  private CustomerDto customerDto;
  private String id;
  private String email;

  @BeforeEach
  public void init() {
    customer = new Customer("Jane", "Doe", "jane.doe@email.com", "1234567890");
    customer.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));

    customerDto = new CustomerDto();
    customerDto.name = "Jane Doe";
    customerDto.emailAddress = "jane.doe@email.com";
    customerDto.phoneNumber = "123-456-7890";

    id = "550e8400-e29b-41d4-a716-446655440000";
    email = "jane.doe@email.com";

  }

  @Test
  public void testGetCustomerById() throws Exception {
    when(customerService.getOneById(id)).thenReturn(customer);

    mockMvc.perform(get("/api/customers/{id}", id)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName").value(customer.getFirstName()))
        .andExpect(jsonPath("$.lastName").value(customer.getLastName()))
        .andExpect(jsonPath("$.email").value(customer.getEmail()))
        .andExpect(jsonPath("$.phone").value(customer.getPhone()))
        .andExpect(jsonPath("$.id").value(customer.getId().toString()));
  }

  @Test
  public void testGCustomerById_NotFound() throws Exception {
    when(customerService.getOneById(id))
        .thenThrow(
            new com.evilcorp.bloom.exception.NotFoundException(String.format("Customer with id %s not found", id)));

    mockMvc.perform(get("/api/customers/{id}", id)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void tstGetAllCustomers() throws Exception {
    Iterable<Customer> customers = Arrays.asList(
        new Customer("Jane", "Doe", "jane.doe@email.com", "1234567890"),
        new Customer("John", "Doe", "john.doe@email.com", "1234567890"));

    when(customerService.getAll()).thenReturn(customers);

    mockMvc.perform(get("/api/customers")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(2))
        .andExpect(jsonPath("$[*].firstName", containsInAnyOrder("Jane", "John")))
        .andExpect(jsonPath("$[*].lastName", containsInAnyOrder("Doe", "Doe")))
        .andExpect(jsonPath("$[*].email", containsInAnyOrder("jane.doe@email.com", "john.doe@email.com")))
        .andExpect(jsonPath("$[*].phone", everyItem(equalTo("1234567890"))));
  }

  @Test
  public void testCreateCustomer() throws Exception {
    doNothing().when(customerService).add(customerDto);

    mockMvc.perform(post("/api/customers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(customerDto)))
        .andExpect(status().isCreated());
  }

  // Scenario: some error in dto (missing field(s), invalid fields)
  // Scenario: duplicate email
  // Scenario: empty json payload

  @Test
  public void testGetCustomerByEmail() throws Exception {
    when(customerService.getOneByEmail(email)).thenReturn(customer);

    mockMvc.perform(post("/api/customers/find-email")
        .contentType(MediaType.APPLICATION_JSON)
        .content(email))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName").value(customer.getFirstName()))
        .andExpect(jsonPath("$.lastName").value(customer.getLastName()))
        .andExpect(jsonPath("$.email").value(customer.getEmail()))
        .andExpect(jsonPath("$.phone").value(customer.getPhone()));
  }

  @Test
  public void testCustomerByEmail_NotFound() throws Exception {
    when(customerService.getOneByEmail(email))
        .thenThrow(
            new com.evilcorp.bloom.exception.NotFoundException(
                String.format("Customer with email %s not found", email)));

    mockMvc.perform(post("/api/customers/find-email")
        .contentType(MediaType.APPLICATION_JSON)
        .content(email))
        .andExpect(status().isNotFound());
  }

}
