package com.pomolex.enduromotorcycleguide.domain.dto;

import com.pomolex.enduromotorcycleguide.persistence.entity.Entity;
import java.util.UUID;

public class EngineAddDto extends Entity {
  private String name;
  private int capacity;
  private boolean isTwoStroke;

  public EngineAddDto(UUID id, String name, int capacity, boolean isTwoStroke) {
    super(id);
    this.name = name;
    this.capacity = capacity;
    this.isTwoStroke = isTwoStroke;
  }

  public String name(){
    return name;
  }

  public int capacity(){
    return capacity;
  }

  public boolean isTwoStroke(){
    return isTwoStroke;
  }
}
