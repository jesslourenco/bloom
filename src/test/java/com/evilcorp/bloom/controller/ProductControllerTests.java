package com.evilcorp.bloom.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.evilcorp.bloom.dto.ProductDto;
import com.evilcorp.bloom.dto.ProductFilterDto;
import com.evilcorp.bloom.exception.GlobalExceptionHandler;
import com.evilcorp.bloom.model.Product;
import com.evilcorp.bloom.service.ProductService;
import com.evilcorp.bloom.exception.NotFoundException;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.lang.module.ModuleDescriptor.Exports;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.mock.MockName;
import org.springframework.beans.factory.annotation.Autowired;

@WebMvcTest(ProductController.class)
@Import(GlobalExceptionHandler.class)
public class ProductControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ProductService productService;

  private Product product;
  private ProductDto dto;
  private ProductFilterDto filterDto;

  @BeforeEach
  public void init() {
    dto = new ProductDto();
    dto.setName("macbook air");
    dto.setDescription("a laptop for the casual user");
    dto.setCategoryId(null);
    dto.setBrandId(1);
    dto.setPrice(3000.67);
    dto.setCost(2000.00);
    dto.setStockQty(0);

    product = new Product("Macbook Air",
        "a laptop for the casual user",
        null,
        1,
        null,
        3000.67,
        2000.00,
        0);

    filterDto = new ProductFilterDto();
    filterDto.page = 0;
    filterDto.categoryId = 1;
  }

  @Test
  public void testAdd() throws Exception {
    doNothing().when(productService).add(dto);

    mockMvc.perform(post("/api/products")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isCreated());
  }

  @Test
  public void testAdd_InvalidDto() throws Exception {
    ProductDto badDto = new ProductDto();

    mockMvc.perform(post("/api/products")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(badDto)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.name").value("Product name cannot be blank"));
  }

  @Test
  public void testAdd_InvalidBrandId() throws Exception {
    doThrow(new DataIntegrityViolationException("constraint [23503]"))
        .when(productService).add(any());

    mockMvc.perform(post("/api/products")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testAdd_InvalidCategoryId() throws Exception {
    doThrow(new DataIntegrityViolationException("constraint [23503]"))
        .when(productService).add(any());

    mockMvc.perform(post("/api/products")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testUpdate() throws Exception {
    Integer id = 1;
    doNothing().when(productService).update(dto, id);

    mockMvc.perform(put("/api/products/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isOk());
  }

  @Test
  public void testUpdate_ProductIdNotFound() throws Exception {
    Integer id = 4;
    doThrow(new NotFoundException(String.format("Product with id %d not found.", id)))
        .when(productService).update(any(), eq(id));

    mockMvc.perform(put("/api/products/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testUpdate_InvalidBrandId() throws Exception {
    Integer id = 2;
    doThrow(new DataIntegrityViolationException("constraint [23503]"))
        .when(productService).update(any(), eq(id));

    mockMvc.perform(put("/api/products/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testUpdate_InvalidCategoryId() throws Exception {
    Integer id = 2;
    doThrow(new DataIntegrityViolationException("constraint [23503]"))
        .when(productService).update(any(), eq(id));

    mockMvc.perform(put("/api/products/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testDeleteyById() throws Exception {
    Integer id = 1;
    doNothing().when(productService).deleteById(id);

    mockMvc.perform(post("/api/products/delete/{id}", id)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  public void testDeleteById_NotFound() throws Exception {
    Integer id = 2;
    doThrow(new NotFoundException(String.format("Product id %d not found", id)))
        .when(productService).deleteById(id);

    mockMvc.perform(post("/api/products/delete/{id}", id)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testSearchByName() throws Exception {
    List<Product> products = new ArrayList<>();
    products.add(product);

    String keyword = "macbook";
    when(productService.searchByName(keyword)).thenReturn(products);

    mockMvc.perform(post("/api/products/search")
        .contentType(MediaType.APPLICATION_JSON)
        .content(keyword))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.size()").value(1))
        .andExpect(jsonPath("$[*].name").value(product.getName()))
        .andExpect(jsonPath("$[*].description").value(product.getDescription()))
        .andExpect(jsonPath("$[*].categoryId").value(product.getCategoryId()))
        .andExpect(jsonPath("$[*].brandId").value(product.getBrandId()))
        .andExpect(jsonPath("$[*].imgUrl").value(product.getImgUrl()))
        .andExpect(jsonPath("$[*].price").value(product.getPrice()))
        .andExpect(jsonPath("$[*].cost").value(product.getCost()))
        .andExpect(jsonPath("$[*].stockQty").value(product.getStockQty()));
  }

}
