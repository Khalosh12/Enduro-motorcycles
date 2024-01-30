package com.pomolex.enduromotorcycleguide.persistence.repository;

import com.pomolex.enduromotorcycleguide.persistence.repository.contracts.EngineRepository;
import com.pomolex.enduromotorcycleguide.persistence.repository.contracts.MotorcycleRepository;
import com.pomolex.enduromotorcycleguide.persistence.repository.contracts.SuspensionRepository;
import com.pomolex.enduromotorcycleguide.persistence.repository.contracts.UserRepository;
import com.pomolex.enduromotorcycleguide.persistence.repository.impl.json.JsonRepositoryFactory;
import org.apache.commons.lang3.NotImplementedException;

/**
 * A factory class for creating repository instances based on the specified factory type.
 */
public abstract class RepositoryFactory {
  public static final int JSON = 1;
  public static final int XML = 2;
  public static final int POSTGRESQL = 3;

  /**
   * Gets the repository factory instance based on the specified factory type.
   * @param whichFactory the factory type to use
   * @return the repository factory instance
   */
  public static RepositoryFactory getRepositoryFactory(int whichFactory) {
    return switch (whichFactory) {
      case JSON -> JsonRepositoryFactory.getInstance();
      case XML -> throw new NotImplementedException("Робота з XML файлами не реалізована.");
      case POSTGRESQL -> throw new NotImplementedException(
          "Робота з СУБД PostgreSQL не реалізована.");
      default -> throw new IllegalArgumentException(
          "Помилка при виборі фабрики репозиторіїв.");
    };
  }

  /**
   * Returns the engine repository instance.
   * @return the engine repository instance
   */
  public abstract EngineRepository getEngineRepository();

  /**
   * Returns the motorcycle repository instance.
   * @return the motorcycle repository instance
   */
  public abstract MotorcycleRepository getMotorcycleRepository();

  /**
   * Returns the suspension repository instance.
   * @return the suspension repository instance
   */
  public abstract SuspensionRepository getSuspensionRepository();

  /**
   * Returns the user repository instance.
   * @return the user repository instance
   */
  public abstract UserRepository getUserRepository();

  /**
   * Commits the changes to the repository.
   */
  public abstract void commit();
}
