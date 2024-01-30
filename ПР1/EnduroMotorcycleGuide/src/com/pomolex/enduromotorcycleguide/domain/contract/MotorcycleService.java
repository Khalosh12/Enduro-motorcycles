package com.pomolex.enduromotorcycleguide.domain.contract;

import com.pomolex.enduromotorcycleguide.domain.Service;
import com.pomolex.enduromotorcycleguide.domain.dto.MotorcycleAddDto;
import com.pomolex.enduromotorcycleguide.domain.dto.SuspensionAddDto;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Engine;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Motorcycle;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Suspension;
import java.util.Set;

public interface MotorcycleService extends Service<Motorcycle> {
  Motorcycle getByName(String name);
  Set<Motorcycle> getByEngine(Engine engine);
  Set<Motorcycle> getBySuspension(Suspension suspension);
  Motorcycle add(MotorcycleAddDto motorcycleAddDto);

}
