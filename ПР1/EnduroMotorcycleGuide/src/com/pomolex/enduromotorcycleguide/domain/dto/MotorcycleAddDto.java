package com.pomolex.enduromotorcycleguide.domain.dto;

import com.pomolex.enduromotorcycleguide.persistence.entity.Entity;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Engine;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Suspension;
import java.util.UUID;

public class MotorcycleAddDto extends Entity {
  private String name;
  private Engine engine;
  private Suspension suspension;
  private String description;

  public MotorcycleAddDto(UUID id, String name, Engine engine, Suspension suspension, String description) {
    super(id);
    this.name = name;
    this.engine = engine;
    this.suspension = suspension;
    this.description = description;
  }

  public String name(){
    return name;
  }
  public Engine engine(){
    return engine;
  }
  public Suspension suspension(){
    return suspension;
  }

  public String description(){
    return description;
  }

}
