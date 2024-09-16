package com.evilcorp.bloom.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.evilcorp.bloom.dto.CategoryDto;
import com.evilcorp.bloom.exception.GlobalExceptionHandler;
import com.evilcorp.bloom.exception.NotFoundException;
import com.evilcorp.bloom.model.Category;
import com.evilcorp.bloom.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(CategoryController.class)
@Import(GlobalExceptionHandler.class)
public class CategoryControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private CategoryService categoryService;

  private Category category;
  private Category subcategory;
  private CategoryDto categoryDto;
  private CategoryDto subcategoryDto;

  @BeforeEach
  public void init() {
    this.category = new Category("Electronics", null);
    category.setId(100);

    this.subcategory = new Category("Laptops", this.category.getId());
    subcategory.setId(101);

    this.categoryDto = new CategoryDto();
    this.categoryDto.category = "electronics";
    this.categoryDto.parentCategoryId = null;

    this.subcategoryDto = new CategoryDto();
    this.subcategoryDto.category = "laptops";
    this.subcategoryDto.parentCategoryId = this.category.getId();
  }

  @Test
  public void testCreateCategory() throws Exception {
    doNothing().when(categoryService).add(categoryDto);

    mockMvc.perform(post("/api/categories")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(categoryDto)))
        .andExpect(status().isCreated());
  }

  @Test
  public void testCreateCategory_CategoryAlreadyExists() throws Exception {
    doThrow(new DataIntegrityViolationException(String.format("Category %s already exists", category.getName())))
        .when(categoryService).add(any());

    mockMvc.perform(post("/api/categories")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(categoryDto)))
        .andExpect(status().isConflict())
        .andExpect(content().string(String.format("Category %s already exists", category.getName())));
  }

  @Test
  public void testCreateCategory_InvalidParentCategory() throws Exception {
    doThrow(new NotFoundException(
        String.format("Parent category with id %d does not exist", subcategoryDto.parentCategoryId)))
        .when(categoryService).add(any());

    mockMvc.perform(post("/api/categories")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(categoryDto)))
        .andExpect(status().isNotFound())
        .andExpect(content().string(
            String.format("Parent category with id %d does not exist", subcategoryDto.parentCategoryId)));
  }

  @Test
  public void testCreateCategory_InvalidDto() throws Exception {
    CategoryDto badDto = new CategoryDto();

    mockMvc.perform(post("/api/categories")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(badDto)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.category").value("Category name cannot be blank"));
  }
}
