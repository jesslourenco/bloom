package com.evilcorp.bloom.service;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import com.evilcorp.bloom.exception.NotFoundException;

import com.evilcorp.bloom.dto.BrandDto;
import com.evilcorp.bloom.model.Brand;
import com.evilcorp.bloom.repo.BrandRepo;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTests {
  @Mock
  private BrandRepo brandRepo;

  @InjectMocks
  private BrandService brandService;

  private Brand brand;
  private BrandDto dto;

  @BeforeEach
  public void init() {
    this.brand = new Brand("Apple");
    brand.setId(1);

    this.dto = new BrandDto();
    dto.name = "apple";
  }

  @Test
  public void testAdd() {
    when(brandRepo.existsByName(brand.getName())).thenReturn(false);
    brandService.add(dto);

    verify(brandRepo)
        .save(argThat(b -> b.getName().equals(brand.getName())));
  }

  @Test
  public void testAdd_BrandAlreadyExists() {
    when(brandRepo.existsByName(brand.getName())).thenReturn(true);

    assertThatThrownBy(() -> brandService.add(dto))
        .isInstanceOf(DataIntegrityViolationException.class);
  }

  @Test
  public void testDelete() {
    when(brandRepo.existsById(brand.getId())).thenReturn(true);
    brandService.delete(brand.getId());

    verify(brandRepo).deleteById(brand.getId());
  }

  @Test
  public void testDelete_BrandNotFound() {
    when(brandRepo.existsById(brand.getId())).thenReturn(false);

    assertThatThrownBy(() -> brandService.delete(brand.getId()))
        .isInstanceOf(NotFoundException.class);
  }
}
