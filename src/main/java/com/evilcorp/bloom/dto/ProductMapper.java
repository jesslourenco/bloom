package com.evilcorp.bloom.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.evilcorp.bloom.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
  @Mapping(target = "id", ignore = true)
  Product toProduct(ProductDto productDto);
}
