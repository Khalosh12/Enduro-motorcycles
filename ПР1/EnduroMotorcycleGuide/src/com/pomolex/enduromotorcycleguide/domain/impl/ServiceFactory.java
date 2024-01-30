package com.pomolex.enduromotorcycleguide.domain.impl;

import com.pomolex.enduromotorcycleguide.domain.contract.AuthService;
import com.pomolex.enduromotorcycleguide.domain.contract.EngineService;
import com.pomolex.enduromotorcycleguide.domain.contract.MotorcycleService;
import com.pomolex.enduromotorcycleguide.domain.contract.SignUpService;
import com.pomolex.enduromotorcycleguide.domain.contract.SuspensionService;
import com.pomolex.enduromotorcycleguide.domain.contract.UserService;
import com.pomolex.enduromotorcycleguide.domain.exception.DependencyException;
import com.pomolex.enduromotorcycleguide.persistence.repository.RepositoryFactory;

/**
 * A factory class for creating instances of the Service interface.
 */
public final class ServiceFactory {

  private static volatile ServiceFactory INSTANCE;
  private final AuthService authService;
  private final EngineService engineService;
  private final MotorcycleService motorcycleService;
  private final SuspensionService suspensionService;
  private final UserService userService;
  private final SignUpService signUpService;
  private final RepositoryFactory repositoryFactory;

  private ServiceFactory(RepositoryFactory repositoryFactory) {
    this.repositoryFactory = repositoryFactory;
    var userRepository = repositoryFactory.getUserRepository();
    authService = new AuthServiceImpl(userRepository);
    engineService = new EngineServiceImpl(
        repositoryFactory.getEngineRepository()
    );
    motorcycleService = new MotorcycleServiceImpl(repositoryFactory.getMotorcycleRepository());
    suspensionService = new SuspensionServiceImpl(repositoryFactory.getSuspensionRepository());
    userService = new UserServiceImpl(userRepository);
    signUpService = new SignUpServiceImpl(userService);
  }

  /**
   * Використовувати, лише якщо впевнені, що існує об'єкт RepositoryFactory.
   *
   * @return екземпляр типу ServiceFactory
   */
  public static ServiceFactory getInstance() {
    if (INSTANCE.repositoryFactory != null) {
      return INSTANCE;
    } else {
      throw new DependencyException(
          "Ви забули створити обєкт RepositoryFactory, перед використанням ServiceFactory.");
    }
  }

  /**
   * Gets the singleton instance of the ServiceFactory based on the specified RepositoryFactory.
   * @param repositoryFactory the repository factory to be used by the service factory
   * @return the singleton instance of the ServiceFactory
   */
  public static ServiceFactory getInstance(RepositoryFactory repositoryFactory) {
    if (INSTANCE == null) {
      synchronized (ServiceFactory.class) {
        if (INSTANCE == null) {
          INSTANCE = new ServiceFactory(repositoryFactory);
        }
      }
    }

    return INSTANCE;
  }

  public AuthService getAuthService() {
    return authService;
  }

  public UserService getUserService() {
    return userService;
  }

  public SignUpService getSignUpService() {
    return signUpService;
  }

  public RepositoryFactory getRepositoryFactory() {
    return repositoryFactory;
  }

  public EngineService getEngineService() {
    return engineService;
  }

  public MotorcycleService getMotorcycleService() {
    return motorcycleService;
  }

  public SuspensionService getSuspensionService() {
    return suspensionService;
  }
}
