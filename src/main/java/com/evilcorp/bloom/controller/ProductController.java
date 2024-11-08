package com.evilcorp.bloom.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.evilcorp.bloom.service.ProductService;
import com.evilcorp.bloom.dto.ProductDto;
import com.evilcorp.bloom.dto.ProductFilterDto;
import com.evilcorp.bloom.model.Product;
import com.evilcorp.bloom.service.PaginatedResult;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void add(@RequestBody @Valid ProductDto dto) {
    productService.add(dto);
  }

  @PutMapping("/{id}")
  public void update(@RequestBody @Valid ProductDto dto, @PathVariable Integer id) {
    productService.update(dto, id);
  }

  @PostMapping("/delete/{id}")
  public void deleteById(@PathVariable Integer id) {
    productService.deleteById(id);
  }

  @PostMapping("/search")
  public List<Product> searchByName(@RequestBody String name) {
    return productService.searchByName(name);
  }

  @GetMapping("/filter/category")
  public PaginatedResult<Product> findAllByCategoryId(@RequestBody @Valid ProductFilterDto dto) {
    return productService.findAllByCategoryId(dto);
  }

  @GetMapping("/{id}")
  public Product findById(@PathVariable Integer id) {
    return productService.findById(id);
  }
}
