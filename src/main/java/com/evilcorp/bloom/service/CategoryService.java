package com.evilcorp.bloom.service;

import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;

import com.evilcorp.bloom.dto.CategoryDto;
import com.evilcorp.bloom.model.Category;
import com.evilcorp.bloom.repo.CategoryRepo;
import com.evilcorp.bloom.exception.NestedSubcategoriesException;
import com.evilcorp.bloom.exception.NotFoundException;

public class CategoryService {

  private final CategoryRepo categoryRepo;

  public CategoryService(CategoryRepo categoryRepo) {
    this.categoryRepo = categoryRepo;
  }

  public void add(CategoryDto dto) {

    dto.category = dto.category.substring(0, 1).toUpperCase() + dto.category.substring(1).toLowerCase();

    Optional<Category> newCategory = categoryRepo.findByName(dto.category);

    if (!newCategory.isEmpty()) {
      throw new DataIntegrityViolationException(String.format("Category %s already exists", dto.category));
    }

    if (dto.parentCategory != null) {
      Category parent = categoryRepo.findById(dto.parentCategory)
          .orElseThrow(() -> new NotFoundException(
              String.format("Parent category with id %d does not exist", dto.parentCategory)));

      if (parent.getParentCategoryId() != null) {
        throw new NestedSubcategoriesException("Parent cannot be a subcategory");
      }
    }

    Category category = new Category(dto.category, dto.parentCategory);
    categoryRepo.save(category);
  }

  public Iterable<Category> getAll() {
    return categoryRepo.findAll();
  }

  public Category getOneByName(String name) {
    return categoryRepo.findByName(name)
        .orElseThrow(() -> new NotFoundException(String.format("Category %s not found", name)));
  }

  public void deleteOneById(Integer id) {
    categoryRepo.findById(id)
        .orElseThrow(() -> new NotFoundException(String.format("Category id %d not found", id)));

    categoryRepo.deleteById(id);
  }
}
