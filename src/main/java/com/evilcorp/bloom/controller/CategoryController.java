package com.evilcorp.bloom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.evilcorp.bloom.dto.CategoryDto;
import com.evilcorp.bloom.model.Category;
import com.evilcorp.bloom.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

  CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createCategory(@RequestBody @Valid CategoryDto dto) {
    categoryService.add(dto);
  }

  @GetMapping
  public Iterable<Category> getAllCategories() {
    return categoryService.getAll();
  }

  @PostMapping("/find-by-name")
  public Category getCategoryByName(String name) {
    return categoryService.getOneByName(name);
  }

  @PostMapping("/delete/{id}")
  public void deleteCategoryById(@PathVariable Integer id) {
    categoryService.deleteOneById(id);
  }
}
