package com.pomolex.enduromotorcycleguide.persistence.repository.impl.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Suspension;
import com.pomolex.enduromotorcycleguide.persistence.repository.contracts.SuspensionRepository;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

final class SuspensionJsonRepositoryImpl
    extends GenericJsonRepository<Suspension>
    implements SuspensionRepository {
  SuspensionJsonRepositoryImpl(Gson gson) {
    super(gson, JsonPathFactory.SUSPENSIONS.getPath(), TypeToken
        .getParameterized(Set.class, Suspension.class)
        .getType());
  }

  @Override
  public Optional<Suspension> findByType(String type) {
    return entities.stream().filter(s -> s.getType().equals(type)).findFirst();
  }
}
