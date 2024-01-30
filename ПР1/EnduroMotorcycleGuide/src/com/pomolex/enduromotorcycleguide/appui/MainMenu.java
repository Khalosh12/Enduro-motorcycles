package com.pomolex.enduromotorcycleguide.appui;

import com.pomolex.enduromotorcycleguide.domain.contract.EngineService;
import com.pomolex.enduromotorcycleguide.domain.contract.MotorcycleService;
import com.pomolex.enduromotorcycleguide.domain.contract.SuspensionService;
import com.pomolex.enduromotorcycleguide.domain.impl.ServiceFactory;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Suspension;
import com.pomolex.enduromotorcycleguide.persistence.repository.RepositoryFactory;
import com.pomolex.enduromotorcycleguide.persistence.repository.contracts.EngineRepository;
import java.util.Scanner;

public class MainMenu {
  /**
   * Displays main menu.
   *
   * @param userName Username of registered user.
   */
  public void registeredUserMenu(String userName) {
    System.out.println("Ласкаво просимо до довідника про мотоцикли для Ендуро, " + userName);
    while (true) {
      System.out.println("1. Двигуни \n2. Мотоцикли \n3. Підвіски \n4. Вихід");
      System.out.println("Оберіть потрібний пункт меню: ");
      Scanner userInput = new Scanner(System.in);
      try {
        RepositoryFactory jsonRepositoryFactory = RepositoryFactory
            .getRepositoryFactory(RepositoryFactory.JSON);
        ServiceFactory serviceFactory = ServiceFactory.getInstance(jsonRepositoryFactory);
        EngineService engineService = serviceFactory.getEngineService();
        SuspensionService suspensionService = serviceFactory.getSuspensionService();

        int userChoice = userInput.nextInt();
        switch (userChoice) {
          case 1:
            EngineMenu engineMenu = new EngineMenu(engineService);
            engineMenu.engineMenu();
            break;
          case 2:
            MotorcycleService motorcycleService = serviceFactory.getMotorcycleService();

            MotorcycleMenu motorcycleMenu = new MotorcycleMenu(motorcycleService, engineService, suspensionService);
            motorcycleMenu.motorcycleMenu();
            break;
          case 3:
            SuspensionMenu suspensionMenu = new SuspensionMenu(suspensionService);
            suspensionMenu.suspensionMenu();
            break;
          case 4:
            System.exit(1);
          default:
            System.out.println("Помилка, введіть числа 1 або 4");
            break;
        }
        System.out.println("Бажаєте продовжити?");
        System.out.println("1. Так");
        System.out.println("2. Ні");
        Scanner userInputChoice = new Scanner(System.in);
        int userChoiceContinue = userInputChoice.nextInt();
        if (userChoiceContinue == 2) {
          System.exit(1);
          break;
        }
      } catch (Exception e) {
        System.out.println("Помилка, спробуйте ще раз");
      }
    }
  }

}
