package com.evilcorp.bloom.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

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

import com.evilcorp.bloom.dto.BrandDto;
import com.evilcorp.bloom.exception.GlobalExceptionHandler;
import com.evilcorp.bloom.exception.NotFoundException;
import com.evilcorp.bloom.model.Brand;
import com.evilcorp.bloom.service.BrandService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BrandController.class)
@Import(GlobalExceptionHandler.class)
public class BrandControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private BrandService brandService;

  private Brand brand;
  private BrandDto brandDto;

  @BeforeEach
  public void init() {
    this.brandDto = new BrandDto();
    brandDto.name = "apple";

    this.brand = new Brand("Apple");
    brand.setId(1);
  }

  @Test
  public void testCreateBrand() throws Exception {
    doNothing().when(brandService).add(brandDto);

    mockMvc.perform(post("/api/brands")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(brandDto)))
        .andExpect(status().isCreated());
  }

  @Test
  public void testCreateBrand_BrandAlreadyExists() throws Exception {
    doThrow(new DataIntegrityViolationException(String.format("Brand %s already exists", brand.getName())))
        .when(brandService).add(any());

    mockMvc.perform(post("/api/brands")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(brandDto)))
        .andExpect(status().isConflict())
        .andExpect(content().string(String.format("Brand %s already exists", brand.getName())));
  }

  @Test
  public void testCreateBrand_InvalidDto() throws Exception {
    BrandDto badDto = new BrandDto();

    mockMvc.perform(post("/api/brands")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(badDto)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.name").value("Brand name cannot be blank."));
  }

  @Test
  public void testDeleteBrand() throws Exception {
    doNothing().when(brandService).delete(brand.getId());

    mockMvc.perform(post("/api/brands/delete/{id}", brand.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(brandDto)))
        .andExpect(status().isOk());
  }

  @Test
  public void testDeleteBrand_NotFound() throws Exception {
    doThrow(new NotFoundException(String.format("Brand with id %d does not exists", brand.getId())))
        .when(brandService).delete(brand.getId());

    mockMvc.perform(post("/api/brands/delete/{id}", brand.getId())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string(String.format("Brand with id %d does not exists", brand.getId())));

  }
}
