package com.pomolex.enduromotorcycleguide.persistence.repository;

import com.pomolex.enduromotorcycleguide.persistence.entity.Entity;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * A generic repository interface for managing entities of type E that extend Entity
 */
public interface Repository<E extends Entity> {

  /**
   * Finds an entity by its UUID identifier.
   * @param id the UUID identifier of the entity to find
   * @return an Optional containing the entity if found, or empty if not found
   */
  Optional<E> findById(UUID id);

  /**
   * Finds all entities of type E.
   * @return a Set containing all entities of type E
   */
  Set<E> findAll();

  /**
   * Finds all entities of type E that match the given filter.
   * @param filter a Predicate used to filter the entities
   * @return a Set containing all entities of type E that match the filter
   */
  Set<E> findAll(Predicate<E> filter);

  /**
   * Adds a new entity of type E to the repository.
   * @param entity the entity to add
   * @return the added entity
   */
  E add(E entity);

  /**
   * Removes an entity of type E from the repository.
   * @param entity the entity to remove
   * @return true if the entity was removed, false if it was not found in the repository
   */
  boolean remove(E entity);

}
