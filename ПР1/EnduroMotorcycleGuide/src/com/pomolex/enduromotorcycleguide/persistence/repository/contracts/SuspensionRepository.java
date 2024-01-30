package com.pomolex.enduromotorcycleguide.persistence.repository.contracts;

import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Suspension;
import com.pomolex.enduromotorcycleguide.persistence.repository.Repository;
import java.util.Optional;
import java.util.Set;

public interface SuspensionRepository extends Repository<Suspension> {
  Optional<Suspension> findByType(String type);
}
