package com.evilcorp.bloom.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.evilcorp.bloom.dto.BrandDto;
import com.evilcorp.bloom.service.BrandService;

@RestController
@RequestMapping("api/brands")
public class BrandController {
  private BrandService brandService;

  public BrandController(BrandService brandService) {
    this.brandService = brandService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void addBrand(BrandDto dto) {
    brandService.add(dto);
  }

  @PostMapping
  public void deleteBrandById(Integer id) {
    brandService.delete(id);
  }

  @GetMapping
  public Iterable<Category> getAllBrands() {
    return brandService.getAll();
  }

}
