package com.pomolex.enduromotorcycleguide.domain.impl;

import com.pomolex.enduromotorcycleguide.domain.contract.EngineService;
import com.pomolex.enduromotorcycleguide.domain.dto.EngineAddDto;

import com.pomolex.enduromotorcycleguide.domain.exception.EntityNotFoundException;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Engine;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.User;
import com.pomolex.enduromotorcycleguide.persistence.repository.contracts.EngineRepository;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import org.mindrot.jbcrypt.BCrypt;

final class EngineServiceImpl
    extends GenericService<Engine>
    implements EngineService {
  private final EngineRepository engineRepository;

  EngineServiceImpl(EngineRepository engineRepository) {
    super(engineRepository);
    this.engineRepository = engineRepository;
  }

  /**
   * Retrieves an engine by its name.
   * @param name the name of the engine to retrieve
   * @return the engine with the specified name
   */
  @Override
  public Engine getByName(String name){
    return engineRepository.findByName(name)
        .orElseThrow(() -> new EntityNotFoundException("Такого двигуна не існує"));
  }

  /**
   * Retrieves all engines with the given capacity.
   * @param capacity the capacity of the engines to retrieve
   * @return a Set containing all engines with the given capacity
   */
  @Override
  public Set<Engine> getByCapacity(int capacity) {
    return engineRepository.findByCapacity(capacity);
  }

  /**
   * Retrieves all entities of type Engine.
   * @return a Set containing all entities of type Engine
   */
  @Override
  public Set<Engine> getAll() {
    return getAll(c -> true);
  }

  /**
   * Retrieves all entities of type Engine that match the given filter.
   * @param filter a Predicate used to filter the entities
   * @return a Set containing all entities of type Engine that match the filter
   */
  @Override
  public Set<Engine> getAll(Predicate<Engine> filter) {
    return engineRepository.findAll(filter);
  }

  /**
   * Adds a new engine based on the provided EngineAddDto.
   * @param engineAddDto the data transfer object for adding a new engine
   * @return the added engine
   */
  @Override
  public Engine add(EngineAddDto engineAddDto) {
    var engine = new Engine(engineAddDto.getId(),
        engineAddDto.name(),
        engineAddDto.capacity(),
        engineAddDto.isTwoStroke());
    engineRepository.add(engine);
    return engine;
  }



}
