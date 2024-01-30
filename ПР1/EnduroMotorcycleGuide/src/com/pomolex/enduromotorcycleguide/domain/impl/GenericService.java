package com.pomolex.enduromotorcycleguide.domain.impl;

import com.pomolex.enduromotorcycleguide.domain.Service;
import com.pomolex.enduromotorcycleguide.domain.exception.EntityNotFoundException;
import com.pomolex.enduromotorcycleguide.persistence.entity.Entity;
import com.pomolex.enduromotorcycleguide.persistence.repository.Repository;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

class GenericService<E extends Entity> implements Service<E> {

  private final Repository<E> repository;

  public GenericService(Repository<E> repository) {
    this.repository = repository;
  }

  /**
   * Retrieves the entity by its UUID identifier.
   * @param id the UUID identifier of the entity to retrieve
   * @return the entity with the given identifier
   */
  @Override
  public E get(UUID id) {
    return repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(
            "Ми не знайшли запис по вказаному ідентифікатору"));
  }

  /**
   * Retrieves all entities of type E.
   * @return a Set containing all entities of type E
   */
  @Override
  public Set<E> getAll() {
    return repository.findAll();
  }

  /**
   * Retrieves all entities of type E that match the given filter.
   * @param filter a Predicate used to filter the entities
   * @return a Set containing all entities of type E that match the filter
   */
  @Override
  public Set<E> getAll(Predicate<E> filter) {
    return repository.findAll(filter);
  }

  /**
   * Adds a new entity of type E.
   * @param entity the entity to add
   * @return the added entity
   */
  @Override
  public E add(E entity) {
    return repository.add(entity);
  }

  /**
   * Removes the specified entity.
   * @param entity the entity to remove
   * @return true if the entity was removed, false if it was not found
   */
  @Override
  public boolean remove(E entity) {
    return repository.remove(entity);
  }
}
