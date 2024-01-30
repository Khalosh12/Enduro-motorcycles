package com.pomolex.enduromotorcycleguide.persistence.repository.impl.json;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.pomolex.enduromotorcycleguide.persistence.entity.Entity;
import com.pomolex.enduromotorcycleguide.persistence.exception.JsonFileIOException;
import com.pomolex.enduromotorcycleguide.persistence.repository.Repository;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A generic JSON-based repository implementation for managing entities of type E.
 * @param <E> the type of the entity
 */
class GenericJsonRepository<E extends Entity> implements Repository<E> {
  protected final Set<E> entities;
  private final Gson gson;
  private final Path path;
  private final Type collectionType;

  GenericJsonRepository(Gson gson, Path path, Type collectionType) {
    this.gson = gson;
    this.path = path;
    this.collectionType = collectionType;
    entities = new HashSet<>(loadAll());
  }

  /**
   * Finds an entity by its UUID identifier.
   * @param id the UUID identifier of the entity to find
   * @return an Optional containing the entity if found, or empty if not found
   */
  @Override
  public Optional<E> findById(UUID id) {
    return entities.stream().filter(e -> e.getId().equals(id)).findFirst();
  }

  /**
   * Finds all entities of type E.
   * @return a Set containing all entities of type E
   */
  @Override
  public Set<E> findAll() {
    return entities;
  }

  /**
   * Finds all entities of type E that match the given filter.
   * @param filter a Predicate used to filter the entities
   * @return a Set containing all entities of type E that match the filter
   */
  @Override
  public Set<E> findAll(Predicate<E> filter) {
    return entities.stream().filter(filter).collect(Collectors.toSet());
  }

  /**
   * Adds a new entity of type E to the repository.
   * @param entity the entity to add
   * @return the added entity
   */
  @Override
  public E add(E entity) {
    entities.remove(entity);
    entities.add(entity);
    return entity;
  }

  /**
   * Removes an entity of type E from the repository.
   * @param entity the entity to remove
   * @return true if the entity was removed, false if it was not found in the repository
   */
  @Override
  public boolean remove(E entity) {
    return entities.remove(entity);
  }

  /**
   * Retrieves the file path of the JSON data.
   * @return the file path of the JSON data
   */
  public Path getPath() {
    return path;
  }

  private Set<E> loadAll() {
    try {
      fileNotFound();
      var json = Files.readString(path);
      return isValidJson(json) ? gson.fromJson(json, collectionType) : new HashSet<>();
    } catch (IOException e) {
      throw new JsonFileIOException("Помилка при роботі із файлом %s. Детальніше %s"
          .formatted(path.getFileName(), System.getProperty("user.dir")));
    }
  }

  /**
   * Перевірка на валідність формату даних JSON.
   *
   * @param input JSON у форматі рядка.
   * @return результат перевірки.
   */
  private boolean isValidJson(String input) {
    try (JsonReader reader = new JsonReader(new StringReader(input))) {
      reader.skipValue();
      return reader.peek() == JsonToken.END_DOCUMENT;
    } catch (IOException e) {
      return false;
    }
  }

  /**
   * Якщо файлу не існує, то ми його створюємо.
   *
   * @throws IOException виключення при роботі із потоком вводу виводу.
   */
  private void fileNotFound() throws IOException {
    if (!Files.exists(path)) {
      Files.createFile(path);
    }
  }

}
