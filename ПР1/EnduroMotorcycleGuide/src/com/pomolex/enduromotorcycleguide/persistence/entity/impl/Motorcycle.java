package com.pomolex.enduromotorcycleguide.persistence.entity.impl;

import com.pomolex.enduromotorcycleguide.persistence.entity.Entity;
import java.util.UUID;

public class Motorcycle extends Entity {
  private String name;
  private Engine engine;
  private Suspension suspension;
  private String description;

  public Motorcycle(UUID id, String name, Engine engine,
      Suspension suspension, String description) {
    super(id);
    this.name = name;
    this.engine = engine;
    this.suspension = suspension;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public Engine getEngine() {
    return engine;
  }

  public Suspension getSuspension() {
    return suspension;
  }

  public String getDescription() {
    return description;
  }
}
