package com.pomolex.enduromotorcycleguide.persistence.entity.impl;

import com.pomolex.enduromotorcycleguide.persistence.entity.Entity;
import java.util.UUID;

public class Engine extends Entity {

  private String name;
  private int capacity;
  private boolean isTwoStroke;

  public Engine(UUID id, String name, int capacity, boolean isTwoStroke) {
    super(id);
    this.name = name;
    this.capacity = capacity;
    this.isTwoStroke = isTwoStroke;
  }

  public String getName() {
    return name;
  }

  public int getCapacity() {
    return capacity;
  }

  public boolean isTwoStroke() {
    return isTwoStroke;
  }
}
