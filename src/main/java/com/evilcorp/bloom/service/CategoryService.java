package com.evilcorp.bloom.service;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.evilcorp.bloom.dto.CategoryDto;
import com.evilcorp.bloom.model.Category;
import com.evilcorp.bloom.repo.CategoryRepo;
import com.evilcorp.bloom.exception.NestedSubcategoriesException;
import com.evilcorp.bloom.exception.NotFoundException;
import com.evilcorp.bloom.util.CapitalizeUtil;

@Service
public class CategoryService {
  private final CategoryRepo categoryRepo;

  public CategoryService(CategoryRepo categoryRepo) {
    this.categoryRepo = categoryRepo;
  }

  public void add(CategoryDto dto) {
    dto.category = CapitalizeUtil.getCapitalizedString(dto.category);

    Optional<Category> newCategory = categoryRepo.findByName(dto.category);

    if (!newCategory.isEmpty()) {
      throw new DataIntegrityViolationException(String.format("Category %s already exists", dto.category));
    }

    if (dto.parentCategoryId != null) {
      Category parent = categoryRepo.findById(dto.parentCategoryId)
          .orElseThrow(() -> new NotFoundException(
              String.format("Parent category with id %d does not exist", dto.parentCategoryId)));

      if (parent.getParentCategoryId() != null) {
        throw new NestedSubcategoriesException("Parent cannot be a subcategory");
      }
    }

    Category category = new Category(dto.category, dto.parentCategoryId);
    categoryRepo.save(category);
  }

  public Iterable<Category> getAll() {
    return categoryRepo.findAll();
  }

  public Category getOneByName(String name) {
    String category = CapitalizeUtil.getCapitalizedString(name);

    return categoryRepo.findByName(category)
        .orElseThrow(() -> new NotFoundException(String.format("Category %s not found", category)));
  }

  public void deleteOneById(Integer id) {
    categoryRepo.findById(id)
        .orElseThrow(() -> new NotFoundException(String.format("Category id %d not found", id)));

    categoryRepo.deleteById(id);
  }
}
