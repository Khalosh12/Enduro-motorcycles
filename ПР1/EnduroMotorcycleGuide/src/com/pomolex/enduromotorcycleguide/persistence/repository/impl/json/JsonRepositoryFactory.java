package com.pomolex.enduromotorcycleguide.persistence.repository.impl.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pomolex.enduromotorcycleguide.persistence.entity.Entity;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Suspension;
import com.pomolex.enduromotorcycleguide.persistence.exception.JsonFileIOException;
import com.pomolex.enduromotorcycleguide.persistence.repository.RepositoryFactory;
import com.pomolex.enduromotorcycleguide.persistence.repository.contracts.EngineRepository;
import com.pomolex.enduromotorcycleguide.persistence.repository.contracts.MotorcycleRepository;
import com.pomolex.enduromotorcycleguide.persistence.repository.contracts.SuspensionRepository;
import com.pomolex.enduromotorcycleguide.persistence.repository.contracts.UserRepository;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

/**
 * A factory for creating JSON-based repository instances.
 */
public class JsonRepositoryFactory extends RepositoryFactory {
  private final Gson gson;
  private EngineJsonRepositoryImpl engineJsonRepositoryImpl;
  private MotorcycleJsonRepositoryImpl motorcycleJsonRepositoryImpl;
  private SuspensionJsonRepositoryImpl suspensionJsonRepositoryImpl;
  private UserJsonRepositoryImpl userJsonRepositoryImpl;

  private JsonRepositoryFactory() {

    GsonBuilder gsonBuilder = new GsonBuilder();

    gson = gsonBuilder.setPrettyPrinting().create();

    engineJsonRepositoryImpl = new EngineJsonRepositoryImpl(gson);
    motorcycleJsonRepositoryImpl = new MotorcycleJsonRepositoryImpl(gson);
    suspensionJsonRepositoryImpl = new SuspensionJsonRepositoryImpl(gson);
    userJsonRepositoryImpl = new UserJsonRepositoryImpl(gson);
  }

  /**
   * Retrieves the singleton instance of the JsonRepositoryFactory.
   * @return the singleton instance of the JsonRepositoryFactory
   */
  public static JsonRepositoryFactory getInstance() {
    return InstanceHolder.INSTANCE;
  }

  @Override
  public EngineRepository getEngineRepository() {
    return engineJsonRepositoryImpl;
  }

  @Override
  public MotorcycleRepository getMotorcycleRepository() {
    return motorcycleJsonRepositoryImpl;
  }

  @Override
  public SuspensionRepository getSuspensionRepository() {
    return suspensionJsonRepositoryImpl;
  }

  @Override
  public UserRepository getUserRepository() {
    return userJsonRepositoryImpl;
  }

  /**
   * Commits the changes to the repository.
   */
  public void commit() {
    serializeEntities(engineJsonRepositoryImpl.getPath(), engineJsonRepositoryImpl.findAll());
    serializeEntities(motorcycleJsonRepositoryImpl.getPath(), motorcycleJsonRepositoryImpl.findAll());
    serializeEntities(suspensionJsonRepositoryImpl.getPath(), suspensionJsonRepositoryImpl.findAll());
    serializeEntities(userJsonRepositoryImpl.getPath(), userJsonRepositoryImpl.findAll());
  }

  private <E extends Entity> void serializeEntities(Path path, Set<E> entities) {
    try (FileWriter writer = new FileWriter(path.toFile())) {
      // Скидуємо файлик, перед збереженням!
      writer.write("");
      // Перетворюємо колекцію користувачів в JSON та записуємо у файл
      gson.toJson(entities, writer);

    } catch (IOException e) {
      throw new JsonFileIOException("Не вдалось зберегти дані у json-файл. Детальніше: %s"
          .formatted(e.getMessage()));
    }
  }

  private static class InstanceHolder {

    public static final JsonRepositoryFactory INSTANCE = new JsonRepositoryFactory();
  }

}
