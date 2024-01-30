package com.pomolex.enduromotorcycleguide.domain.contract;

import com.pomolex.enduromotorcycleguide.domain.Service;
import com.pomolex.enduromotorcycleguide.domain.dto.EngineAddDto;
import com.pomolex.enduromotorcycleguide.domain.dto.SuspensionAddDto;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Engine;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Suspension;
import java.util.Set;

public interface SuspensionService extends Service<Suspension> {
  Suspension getByType(String type);

  Suspension add(SuspensionAddDto suspensionAddDto);
}
