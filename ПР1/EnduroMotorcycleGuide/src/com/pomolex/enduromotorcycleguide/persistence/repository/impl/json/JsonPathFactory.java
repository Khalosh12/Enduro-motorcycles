package com.pomolex.enduromotorcycleguide.persistence.repository.impl.json;

import java.nio.file.Path;

enum JsonPathFactory {
  USERS("users.json"),
  ENGINES("engines.json"),
  MOTORCYCLES("motorcycles.json"),
  SUSPENSIONS("suspensions.json");

  private static final String DATA_DIRECTORY = "Data";
  private final String fileName;

  JsonPathFactory(String fileName) {
    this.fileName = fileName;
  }

  public Path getPath() {
    return Path.of(DATA_DIRECTORY, this.fileName);
  }
}
