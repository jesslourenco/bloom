package com.evilcorp.bloom.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.evilcorp.bloom.dto.ProductFilterDto;
import com.evilcorp.bloom.dto.ProductDto;
import com.evilcorp.bloom.dto.ProductMapper;
import com.evilcorp.bloom.exception.NotFoundException;
import com.evilcorp.bloom.model.Product;
import com.evilcorp.bloom.repo.ProductRepo;
import com.evilcorp.bloom.util.CapitalizeUtil;

@Service
public class ProductService {
  private final ProductRepo productRepo;
  private final ProductMapper productMapper;

  private static final int pageSize = 10;
  private static final int psqlFKConstraintCode = 23503;

  public ProductService(ProductRepo productRepo, ProductMapper productMapper) {
    this.productRepo = productRepo;
    this.productMapper = productMapper;
  }

  public void add(ProductDto dto) {
    dto.setName(CapitalizeUtil.getCapitalizedString(dto.getName()));
    Product product = productMapper.toProduct(dto);

    try {
      productRepo.save(product);

    } catch (DataIntegrityViolationException e) {
      if (e.getMessage() != null && e.getMessage().contains(String.format("constraint [%d]", psqlFKConstraintCode))) {
        throw new NotFoundException(String.format("Foreign key violation: %s", e.getMessage()));
      } else {
        throw e;
      }
    }
  }

  public void update(ProductDto dto, Integer id) {
    if (!productRepo.existsById(id)) {
      throw new NotFoundException(String.format("Product with id %d not found.", id));
    }

    dto.setName(CapitalizeUtil.getCapitalizedString(dto.getName()));
    Product product = productMapper.toProduct(dto);

    product.setId(id);

    try {
      productRepo.save(product);
    } catch (DataIntegrityViolationException e) {
      if (e.getMessage() != null && e.getMessage().contains(String.format("constraint [%d]", psqlFKConstraintCode))) {
        throw new NotFoundException(String.format("Foreign key violation: %s", e.getMessage()));
      } else {
        throw e;
      }
    }
  }

  public List<Product> searchByName(String keyword) {
    return productRepo.findByNameContaining(keyword);
  }

  /**
   * Returns a paginated result of all products of a certain category.
   * Result is ordered by product's id.
   */
  public PaginatedResult<Product> findAllByCategoryId(ProductFilterDto dto) {
    List<Product> products = productRepo.findAllPaged(dto.categoryId, pageSize, pageSize * dto.page);

    long totalRecords = productRepo.countByCategory(dto.categoryId);
    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

    return new PaginatedResult<>(products, totalRecords, totalPages, dto.page, pageSize, dto.categoryId);
  }

  public Product findById(Integer id) {
    return productRepo.findById(id)
        .orElseThrow(() -> new NotFoundException(String.format("Product with id %d not found.", id)));
  }

  public void deleteById(Integer id) {
    try {
      productRepo.deleteById(id);

    } catch (EmptyResultDataAccessException e) {
      throw new NotFoundException(String.format("Product with id %d not found.", id));
    }
  }
}
