package com.evilcorp.bloom.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;

public class Category {

  @Id
  private UUID id;

  private String name;
  private UUID parent_category_id;

  public Category(String name, UUID parent_category_id) {
    this.name = name;
    this.parent_category_id = parent_category_id;
  }

  public String getName() {
    return this.name;
  }

  public UUID getId() {
    return this.id;
  }

  public UUID getParentCategoryId() {
    return this.parent_category_id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setParentCategoryId(UUID parent_category_id) {
    this.parent_category_id = parent_category_id;
  }
}
