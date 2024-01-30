package com.pomolex.enduromotorcycleguide.domain;

import com.pomolex.enduromotorcycleguide.persistence.entity.Entity;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * A generic service interface for managing entities of type E.
 * @param <E> the type of the entity
 */
public interface Service<E extends Entity> {
  /**
   * Retrieves the entity by its UUID identifier.
   * @param id the UUID identifier of the entity to retrieve
   * @return the entity with the given identifier
   */
  E get(UUID id);

  /**
   * Retrieves all entities of type E.
   * @return a Set containing all entities of type E
   */
  Set<E> getAll();

  /**
   * Retrieves all entities of type E that match the given filter.
   * @param filter a Predicate used to filter the entities
   * @return a Set containing all entities of type E that match the filter
   */
  Set<E> getAll(Predicate<E> filter);

  /**
   * Adds a new entity of type E.
   * @param entity the entity to add
   * @return the added entity
   */
  E add(E entity);

  /**
   * Removes the specified entity.
   * @param entity the entity to remove
   * @return true if the entity was removed, false if it was not found
   */
  boolean remove(E entity);

}
