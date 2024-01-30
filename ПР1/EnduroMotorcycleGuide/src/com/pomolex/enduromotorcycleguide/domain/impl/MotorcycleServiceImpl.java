package com.pomolex.enduromotorcycleguide.domain.impl;

import com.pomolex.enduromotorcycleguide.domain.contract.MotorcycleService;
import com.pomolex.enduromotorcycleguide.domain.dto.MotorcycleAddDto;
import com.pomolex.enduromotorcycleguide.domain.dto.SuspensionAddDto;
import com.pomolex.enduromotorcycleguide.domain.exception.EntityNotFoundException;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Engine;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Motorcycle;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Suspension;
import com.pomolex.enduromotorcycleguide.persistence.repository.contracts.MotorcycleRepository;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

final class MotorcycleServiceImpl
    extends GenericService<Motorcycle>
    implements MotorcycleService {

  private final MotorcycleRepository motorcycleRepository;

  MotorcycleServiceImpl(MotorcycleRepository motorcycleRepository) {
    super(motorcycleRepository);
    this.motorcycleRepository = motorcycleRepository;
  }

  @Override
  public Motorcycle getByName(String name) {
    return motorcycleRepository.findByName(name)
        .orElseThrow(() -> new EntityNotFoundException("Такого мотоцикла не існує"));
  }

  @Override
  public Set<Motorcycle> getByEngine(Engine engine) {
    return motorcycleRepository.findByEngine(engine);
  }

  @Override
  public Set<Motorcycle> getBySuspension(Suspension suspension) {
    return motorcycleRepository.findBySuspension(suspension);
  }

  @Override
  public Set<Motorcycle> getAll() {
    return getAll(c -> true);
  }

  @Override
  public Set<Motorcycle> getAll(Predicate<Motorcycle> filter) {
    return motorcycleRepository.findAll(filter);
  }

  @Override
  public Motorcycle add(MotorcycleAddDto motorcycleAddDto) {
    var motorcycle = new Motorcycle(motorcycleAddDto.getId(),
        motorcycleAddDto.name(),
        motorcycleAddDto.engine(),
        motorcycleAddDto.suspension(),
        motorcycleAddDto.description());
    motorcycleRepository.add(motorcycle);
    return motorcycle;
  }
}
