package com.evilcorp.bloom.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.evilcorp.bloom.dto.BrandDto;
import com.evilcorp.bloom.exception.NotFoundException;
import com.evilcorp.bloom.model.Brand;
import com.evilcorp.bloom.repo.BrandRepo;
import com.evilcorp.bloom.util.CapitalizeUtil;

@Service
public class BrandService {
  private final BrandRepo brandRepo;

  public BrandService(BrandRepo brandRepo) {
    this.brandRepo = brandRepo;
  }

  public void add(BrandDto dto) {
    dto.name = CapitalizeUtil.getCapitalizedString(dto.name);

    if (brandRepo.existsByName(dto.name)) {
      throw new DataIntegrityViolationException(String.format("Brand %s already exists.", dto.name));
    }

    Brand brand = new Brand(dto.name);

    brandRepo.save(brand);
  }

  public void delete(Integer id) {
    if (!brandRepo.existsById(id)) {
      throw new NotFoundException(String.format("Brand with id %d does not exists", id));
    }

    brandRepo.deleteById(id);
  }

  public Iterable<Brand> getAll() {
    return brandRepo.findAll();
  }

}
