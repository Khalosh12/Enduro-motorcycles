package com.pomolex.enduromotorcycleguide.domain.impl;

import com.pomolex.enduromotorcycleguide.domain.contract.SuspensionService;
import com.pomolex.enduromotorcycleguide.domain.dto.EngineAddDto;
import com.pomolex.enduromotorcycleguide.domain.dto.SuspensionAddDto;
import com.pomolex.enduromotorcycleguide.domain.exception.EntityNotFoundException;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Engine;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Suspension;
import com.pomolex.enduromotorcycleguide.persistence.repository.contracts.SuspensionRepository;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

final class SuspensionServiceImpl
    extends GenericService<Suspension>
    implements SuspensionService {
  private final SuspensionRepository suspensionRepository;

  SuspensionServiceImpl(SuspensionRepository suspensionRepository) {
    super(suspensionRepository);
    this.suspensionRepository = suspensionRepository;
  }

  @Override
  public Suspension getByType(String type) {
    return suspensionRepository.findByType(type)
        .orElseThrow(() -> new EntityNotFoundException("Такої підвіски не існує"));
  }

  @Override
  public Set<Suspension> getAll() {
    return getAll(c -> true);
  }

  @Override
  public Set<Suspension> getAll(Predicate<Suspension> filter) {
    return suspensionRepository.findAll(filter);
  }

  @Override
  public Suspension add(SuspensionAddDto suspensionAddDto) {
    var suspension = new Suspension(suspensionAddDto.getId(),
        suspensionAddDto.type(),
        suspensionAddDto.suspensionTravel());
    suspensionRepository.add(suspension);
    return suspension;
  }

}
