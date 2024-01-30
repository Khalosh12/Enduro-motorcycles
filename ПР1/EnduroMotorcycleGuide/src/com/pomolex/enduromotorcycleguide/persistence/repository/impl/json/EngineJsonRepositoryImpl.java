package com.pomolex.enduromotorcycleguide.persistence.repository.impl.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Engine;
import com.pomolex.enduromotorcycleguide.persistence.repository.contracts.EngineRepository;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A JSON-based implementation of the EngineRepository interface.
 */
final class EngineJsonRepositoryImpl
    extends GenericJsonRepository<Engine>
    implements EngineRepository {
  EngineJsonRepositoryImpl(Gson gson) {
    super(gson, JsonPathFactory.ENGINES.getPath(), TypeToken
        .getParameterized(Set.class, Engine.class)
        .getType());
  }

  /**
   * Finds an engine by its name.
   * @param name the name of the engine to find
   * @return an Optional containing the engine if found, or empty if not found
   */
  public Optional<Engine> findByName(String name){
    return entities.stream().filter(e -> e.getName().equals(name)).findFirst();
  }
  /**
   * Finds all engines with the given capacity.
   * @param capacity the capacity of the engines to find
   * @return a Set containing all engines with the given capacity
   */
  @Override
  public Set<Engine> findByCapacity(int capacity) {
    return entities.stream().filter(e -> e.getCapacity() == capacity).collect(Collectors.toSet());
  }

}
