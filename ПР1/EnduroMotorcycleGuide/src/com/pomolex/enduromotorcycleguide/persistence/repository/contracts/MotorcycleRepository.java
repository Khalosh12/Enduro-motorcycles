package com.pomolex.enduromotorcycleguide.persistence.repository.contracts;

import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Engine;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Motorcycle;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Suspension;
import com.pomolex.enduromotorcycleguide.persistence.repository.Repository;
import java.util.Optional;
import java.util.Set;

public interface MotorcycleRepository extends Repository<Motorcycle> {
  Optional<Motorcycle> findByName(String name);
  Set<Motorcycle> findByEngine(Engine engine);
  Set<Motorcycle> findBySuspension(Suspension suspension);
}
