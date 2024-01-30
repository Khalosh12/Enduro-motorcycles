package com.pomolex.enduromotorcycleguide.persistence.repository.contracts;

import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Engine;
import com.pomolex.enduromotorcycleguide.persistence.repository.Repository;
import java.util.Optional;
import java.util.Set;

/**
 * A repository for managing Engine entities.
 */
public interface EngineRepository extends Repository<Engine> {

  /**
   * Finds an engine by its name.
   * @param name the name of the engine to find
   * @return an Optional containing the engine if found, or empty if not found
   */
  Optional<Engine> findByName(String name);

  /**
   * Finds all engines with the given capacity.
   * @param capacity the capacity of the engines to find
   * @return a Set containing all engines with the given capacity
   */
  Set<Engine> findByCapacity(int capacity);
}
