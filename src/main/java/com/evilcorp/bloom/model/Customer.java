package com.evilcorp.bloom.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@Table("customers")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Customer {
  @Id
  private UUID id;

  private String firstName;
  private String lastName;
  private String email;
  private String phone;

  public Customer(String firstName, String lastName, String email, String phone) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
  }

}
