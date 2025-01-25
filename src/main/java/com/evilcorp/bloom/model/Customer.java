package com.evilcorp.bloom.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("customers")
@Getter
@Setter
@NoArgsConstructor
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
