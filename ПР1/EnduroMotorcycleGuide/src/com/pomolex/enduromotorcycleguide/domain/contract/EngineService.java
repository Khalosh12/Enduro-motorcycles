package com.pomolex.enduromotorcycleguide.domain.contract;

import com.pomolex.enduromotorcycleguide.domain.Service;
import com.pomolex.enduromotorcycleguide.domain.dto.EngineAddDto;
import com.pomolex.enduromotorcycleguide.domain.dto.UserAddDto;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Engine;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.User;
import java.util.Set;

/**
 * An interface for managing Engine entities.
 */
public interface EngineService extends Service<Engine> {

  /**
   * Retrieves an engine by its name.
   * @param name the name of the engine to retrieve
   * @return the engine with the specified name
   */
  Engine getByName(String name);

  /**
   * Retrieves all engines with the given capacity.
   * @param capacity the capacity of the engines to retrieve
   * @return a Set containing all engines with the given capacity
   */
  Set<Engine> getByCapacity(int capacity);

  /**
   * Adds a new engine based on the provided EngineAddDto.
   * @param engineAddDto the data transfer object for adding a new engine
   * @return the added engine
   */
  Engine add(EngineAddDto engineAddDto);
}
