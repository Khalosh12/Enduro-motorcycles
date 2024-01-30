package com.pomolex.enduromotorcycleguide.domain.contract;

import com.pomolex.enduromotorcycleguide.domain.Service;
import com.pomolex.enduromotorcycleguide.domain.dto.UserAddDto;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.User;

public interface UserService extends Service<User> {
  User getByUsername(String username);

  User getByEmail(String email);

  User add(UserAddDto userAddDto);

}
