package com.pomolex.enduromotorcycleguide.persistence.repository.contracts;

import com.pomolex.enduromotorcycleguide.persistence.entity.impl.User;
import com.pomolex.enduromotorcycleguide.persistence.repository.Repository;
import java.util.Optional;

public interface UserRepository extends Repository<User> {
  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);
}
