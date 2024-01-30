package com.pomolex.enduromotorcycleguide.domain.dto;

import com.pomolex.enduromotorcycleguide.persistence.entity.Entity;
import java.util.UUID;

public class SuspensionAddDto extends Entity {
  private String type;
  private float suspensionTravel;

  public SuspensionAddDto(UUID id, String type, float suspensionTravel) {
    super(id);
    this.type = type;
    this.suspensionTravel = suspensionTravel;
  }

  public String type(){
    return type;
  }

  public float suspensionTravel(){
    return suspensionTravel;
  }

}
