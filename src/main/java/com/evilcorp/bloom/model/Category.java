package com.evilcorp.bloom.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("category")
public class Category {
  @Id
  private Integer id;

  private String name;
  private Integer parent_category_id;

  public Category(String name, Integer parent_category_id) {
    this.name = name;
    this.parent_category_id = parent_category_id;
  }

  public String getName() {
    return this.name;
  }

  public Integer getId() {
    return this.id;
  }

  public Integer getParentCategoryId() {
    return this.parent_category_id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setParentCategoryId(Integer parent_category_id) {
    this.parent_category_id = parent_category_id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}
