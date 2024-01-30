package com.pomolex.enduromotorcycleguide.persistence.repository.impl.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Engine;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Motorcycle;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Suspension;
import com.pomolex.enduromotorcycleguide.persistence.repository.contracts.MotorcycleRepository;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

final class MotorcycleJsonRepositoryImpl
    extends GenericJsonRepository<Motorcycle>
    implements MotorcycleRepository {
  MotorcycleJsonRepositoryImpl(Gson gson) {
    super(gson, JsonPathFactory.MOTORCYCLES.getPath(), TypeToken
        .getParameterized(Set.class, Motorcycle.class)
        .getType());
  }

  @Override
  public Optional<Motorcycle> findByName(String name) {
    return entities.stream().filter(m -> m.getName().equals(name)).findFirst();
  }

  @Override
  public Set<Motorcycle> findByEngine(Engine engine) {
    return entities.stream().filter(m -> m.getEngine().equals(engine)).collect(Collectors.toSet());
  }

  @Override
  public Set<Motorcycle> findBySuspension(Suspension suspension) {
    return entities.stream().filter(m -> m.getSuspension().equals(suspension)).collect(Collectors.toSet());
  }

}
