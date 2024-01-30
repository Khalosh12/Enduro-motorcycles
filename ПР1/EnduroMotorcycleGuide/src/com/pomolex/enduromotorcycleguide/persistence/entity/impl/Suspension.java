package com.pomolex.enduromotorcycleguide.persistence.entity.impl;

import com.pomolex.enduromotorcycleguide.persistence.entity.Entity;
import java.util.UUID;

public class Suspension extends Entity {
  private String type;

  // Хід підвіски
  private float suspensionTravel;

  public Suspension(UUID id, String type, float suspensionTravel) {
    super(id);
    this.type = type;
    this.suspensionTravel = suspensionTravel;
  }

  public String getType() {
    return type;
  }

  public float getSuspensionTravel() {
    return suspensionTravel;
  }
}
