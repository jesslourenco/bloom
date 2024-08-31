package com.evilcorp.bloom.service;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties.Data;
import org.springframework.dao.DataIntegrityViolationException;

import com.evilcorp.bloom.repo.CategoryRepo;
import com.evilcorp.bloom.dto.CategoryDto;
import com.evilcorp.bloom.model.Category;
import com.evilcorp.bloom.exception.NestedSubcategoriesException;
import com.evilcorp.bloom.exception.NotFoundException;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {
  @Mock
  private CategoryRepo categoryRepo;

  @InjectMocks
  private CategoryService categoryService;

  private Category category;
  private Category categoryWithParent;

  @BeforeEach
  public void init() {
    this.category = new Category("Electronics", null);
    category.setId(100);

    this.categoryWithParent = new Category("Laptops", this.category.getId());
    categoryWithParent.setId(101);
  }

  @Test
  public void testAdd_Success() {
    CategoryDto dto = new CategoryDto();
    dto.category = "electronics";
    dto.parentCategory = null;

    when(categoryRepo.findByName(category.getName())).thenReturn(Optional.empty());
    categoryService.add(dto);

    verify(categoryRepo, times(1)).findByName(category.getName());
    verify(categoryRepo, times(0)).findById(anyInt());

    verify(categoryRepo, times(1))
        .save(argThat(c -> c.getName().equals(category.getName()) &&
            c.getParentCategoryId() == null));
  }

  @Test
  public void testAdd_SuccessSubCategory() {
    CategoryDto dto = new CategoryDto();
    dto.category = "laptops";
    dto.parentCategory = category.getId();

    categoryWithParent.setParentCategoryId(dto.parentCategory);

    when(categoryRepo.findByName(categoryWithParent.getName())).thenReturn(Optional.empty());
    when(categoryRepo.findById(dto.parentCategory)).thenReturn(Optional.of(category));
    categoryService.add(dto);

    verify(categoryRepo, times(1)).findByName(categoryWithParent.getName());
    verify(categoryRepo, times(1)).findById(dto.parentCategory);

    verify(categoryRepo, times(1))
        .save(argThat(c -> c.getName().equals(categoryWithParent.getName()) &&
            c.getParentCategoryId().equals(categoryWithParent.getParentCategoryId())));
  }

  @Test
  public void testAdd_CategoryAlreadyExists() {
    CategoryDto dto = new CategoryDto();
    dto.category = "electronics";
    dto.parentCategory = null;

    when(categoryRepo.findByName(category.getName())).thenReturn(Optional.of(
        category));

    assertThatThrownBy(() -> categoryService.add(dto))
        .isInstanceOf(DataIntegrityViolationException.class);

    verify(categoryRepo, times(1)).findByName(category.getName());
    verify(categoryRepo, times(0)).findById(anyInt());
  }

  @Test
  public void testAdd_ParentDoesNotExist() {
    CategoryDto dto = new CategoryDto();
    dto.category = "laptops";
    dto.parentCategory = category.getId();

    when(categoryRepo.findByName(categoryWithParent.getName())).thenReturn(Optional.empty());
    when(categoryRepo.findById(dto.parentCategory)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> categoryService.add(dto))
        .isInstanceOf(NotFoundException.class);

    verify(categoryRepo, times(1)).findByName(categoryWithParent.getName());
    verify(categoryRepo, times(1)).findById(dto.parentCategory);
  }

  @Test
  public void testAdd_ParentIsSubcategory() {
    CategoryDto dto = new CategoryDto();
    dto.category = "Macbooks";
    dto.parentCategory = categoryWithParent.getId();

    when(categoryRepo.findByName(dto.category)).thenReturn(Optional.empty());
    when(categoryRepo.findById(dto.parentCategory)).thenReturn(Optional.of(categoryWithParent));

    assertThatThrownBy(() -> categoryService.add(dto))
        .isInstanceOf(NestedSubcategoriesException.class);

    verify(categoryRepo, times(1)).findByName(dto.category);
    verify(categoryRepo, times(1)).findById(dto.parentCategory);
  }

}
