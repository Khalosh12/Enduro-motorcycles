package com.pomolex.enduromotorcycleguide.domain.contract;

import com.pomolex.enduromotorcycleguide.domain.dto.UserAddDto;
import java.util.function.Supplier;

/**
 * An interface for user sign-up operations.
 */
public interface SignUpService {
  /**
   * Signs up a new user based on the provided user data and waits for user input.
   * @param userAddDto the data transfer object containing the user's information
   * @param waitForUserInput the supplier for waiting for user input
   */
  void signUp(UserAddDto userAddDto, Supplier<String> waitForUserInput);

}
