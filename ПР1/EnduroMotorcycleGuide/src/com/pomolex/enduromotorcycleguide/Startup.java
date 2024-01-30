package com.pomolex.enduromotorcycleguide;

import com.pomolex.enduromotorcycleguide.appui.BaseMenu;
import com.pomolex.enduromotorcycleguide.domain.impl.ServiceFactory;
import com.pomolex.enduromotorcycleguide.persistence.repository.RepositoryFactory;

final class Startup {

  static void init() {
    RepositoryFactory jsonRepositoryFactory = RepositoryFactory
        .getRepositoryFactory(RepositoryFactory.JSON);
    ServiceFactory serviceFactory = ServiceFactory.getInstance(jsonRepositoryFactory);


    BaseMenu baseMenu = new BaseMenu();
    baseMenu.baseMenu();


    //jsonRepositoryFactory.commit();
  }
}
