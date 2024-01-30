package com.pomolex.enduromotorcycleguide.appui;

import com.pomolex.enduromotorcycleguide.domain.contract.EngineService;
import com.pomolex.enduromotorcycleguide.domain.contract.MotorcycleService;
import com.pomolex.enduromotorcycleguide.domain.contract.SuspensionService;
import com.pomolex.enduromotorcycleguide.domain.dto.MotorcycleAddDto;
import com.pomolex.enduromotorcycleguide.domain.dto.SuspensionAddDto;
import com.pomolex.enduromotorcycleguide.domain.exception.EntityNotFoundException;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Engine;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Motorcycle;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Suspension;
import com.pomolex.enduromotorcycleguide.persistence.repository.RepositoryFactory;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

public class MotorcycleMenu {

  private final MotorcycleService motorcycleService;
  private final EngineService engineService;
  private final SuspensionService suspensionService;

  public MotorcycleMenu(MotorcycleService motorcycleService, EngineService engineService,
      SuspensionService suspensionService) {
    this.motorcycleService = motorcycleService;
    this.engineService = engineService;
    this.suspensionService = suspensionService;
  }

  /**
   * Displays motorcycle menu.
   */
  public void motorcycleMenu() {
    System.out.println("Оберіть потрібний пункт");
    while (true) {
      System.out.println(
          "1. Переглянути \n2. Пошук \n3. Додати \n4. Видалити \n5. Редагувати \n6. Фільтрувати за двигуном "
              + "\n7. Фільтрувати за підвіскою. \n8. Вихід");
      System.out.println("Оберіть потрібний пункт меню: ");
      Scanner userInput = new Scanner(System.in);
      try {
        RepositoryFactory jsonRepositoryFactory = RepositoryFactory
            .getRepositoryFactory(RepositoryFactory.JSON);

        int userChoice = userInput.nextInt();
        switch (userChoice) {
          case 1:
            viewMotorcycles();
            break;
          case 2:
            searchMotorcycle();
            break;
          case 3:
            addMotorcycle();
            jsonRepositoryFactory.commit();
            break;
          case 4:
            deleteMotorcycle();
            jsonRepositoryFactory.commit();
            break;
          case 5:
            updateMotorcycle();
            jsonRepositoryFactory.commit();
            break;
          case 6:
            filterByEngine();
            break;
          case 7:
            filterBySuspension();
            break;
          case 8:
            return;
          default:
            System.out.println("Помилка, введіть числа 1 або 8");
            break;
        }

        System.out.println("Бажаєте продовжити?");
        System.out.println("1. Так");
        System.out.println("2. Ні");
        Scanner userInputChoice = new Scanner(System.in);
        int userChoiceContinue = userInputChoice.nextInt();
        if (userChoiceContinue == 2) {
          break;
        }
      } catch (Exception e) {
        System.out.println("Помилка, спробуйте ще раз");
      }
    }
  }

  private void viewMotorcycles() {
    Set<Motorcycle> motorcycles = motorcycleService.getAll();
    if (!motorcycles.isEmpty()) {
      int i = 1;
      System.out.println("Номер, назва, двигун, підвіска, опис");
      for (Motorcycle motorcycle : motorcycles) {
        System.out.printf("%d. %s, %s, %s, %s %n", i, motorcycle.getName(),
            motorcycle.getEngine().getName(),
            motorcycle.getSuspension().getType(), motorcycle.getDescription());
        i++;
      }
    } else {
      System.out.println("Список мотоциклів пустий");
    }
  }

  private void searchMotorcycle() {
    System.out.println("Введіть назву мотоциклу: ");
    Scanner motorcycleNameInput = new Scanner(System.in, Charset.forName(
        System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251"
            : "UTF-8"));
    try {
      String motorcycleName = motorcycleNameInput.nextLine();
      Motorcycle motorcycle = motorcycleService.getByName(motorcycleName);
      System.out.println("Назва, двигун, підвіска, опис");
      System.out.printf("%s, %s, %s, %s %n", motorcycle.getName(),
          motorcycle.getEngine().getName(),
          motorcycle.getSuspension().getType(), motorcycle.getDescription());
    } catch (EntityNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      System.out.println("Помилка, спробуйте ще раз");
    }
  }

  private void addMotorcycle() {
    System.out.print("Введіть назву мотоциклу: ");
    try {
      Scanner nameInput = new Scanner(System.in, Charset.forName(
          System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251"
              : "UTF-8"));
      String name = nameInput.nextLine();

      Set<Engine> engines = engineService.getAll();
      System.out.println("\nНазва, місткість, чи двотактний");
      for (Engine engine : engines) {
        System.out.printf("%s, %d, %s%n", engine.getName(), engine.getCapacity(),
            engine.isTwoStroke());
      }
      System.out.print("Введіть назву двигуна: ");
      Scanner engineNameInput = new Scanner(System.in, Charset.forName(
          System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251"
              : "UTF-8"));
      String engineName = engineNameInput.nextLine();
      Engine engine = engineService.getByName(engineName);

      Set<Suspension> suspensions = suspensionService.getAll();
      System.out.println("Введіть тип підвіски: ");
      for (Suspension suspension : suspensions) {
        System.out.printf("%s, %f%n", suspension.getType(), suspension.getSuspensionTravel());
      }

      Scanner suspensionTypeInput = new Scanner(System.in, Charset.forName(
          System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251"
              : "UTF-8"));
      String suspensionType = suspensionTypeInput.nextLine();
      Suspension suspension = suspensionService.getByType(suspensionType);

      System.out.println("Введіть додатковий опис: ");
      Scanner descriptionInput = new Scanner(System.in, Charset.forName(
          System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251"
              : "UTF-8"));
      String description = descriptionInput.nextLine();

      MotorcycleAddDto motorcycle = new MotorcycleAddDto(UUID.randomUUID(), name, engine,
          suspension, description);
      if (motorcycleService.getAll(m -> m.getName().equals(name)).isEmpty()) {
        motorcycleService.add(motorcycle);
        System.out.println("Дані успішно додані");
      } else {
        System.out.println("Такий мотоцикл вже існує");
      }
    } catch (EntityNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      System.out.println("Помилка, спробуйте ще раз");
    }
  }

  private void deleteMotorcycle() {
    viewMotorcycles();
    System.out.print("Введіть назву мотоцикла: ");
    Scanner userInput = new Scanner(System.in, Charset.forName(
        System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251"
            : "UTF-8"));
    try {
      String userChoice = userInput.nextLine();
      Motorcycle deletedMotorcycle = motorcycleService.getByName(userChoice);
      motorcycleService.remove(deletedMotorcycle);
      System.out.println("Дані успішно видалено");

    } catch (EntityNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      System.out.println("Помилка, спробуйте ще раз");
    }
  }

  private void updateMotorcycle() {
    viewMotorcycles();
    try {
      if (!engineService.getAll().isEmpty() && !suspensionService.getAll().isEmpty()) {
        System.out.print("Введіть назву мотоциклу: ");
        Scanner userInput = new Scanner(System.in, Charset.forName(
            System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251"
                : "UTF-8"));
        String userChoice = userInput.nextLine();
        if (motorcycleService.getAll(m -> m.getName().equals(userChoice)).isEmpty()) {
          System.out.println("Такого мотоциклу не існує");
          return;
        }
        System.out.print("Введіть нову назву: ");
        Scanner nameInput = new Scanner(System.in, Charset.forName(
            System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251"
                : "UTF-8"));
        String name = nameInput.nextLine();

        Set<Engine> engines = engineService.getAll();
        System.out.println("\nНазва, місткість, чи двотактний");
        for (Engine engine : engines) {
          System.out.printf("%s, %d, %s%n", engine.getName(), engine.getCapacity(),
              engine.isTwoStroke() ? "Так" : "Ні");
        }
        System.out.print("Введіть нову назву двигуна: ");
        Scanner engineNameInput = new Scanner(System.in, Charset.forName(
            System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251"
                : "UTF-8"));
        String engineName = engineNameInput.nextLine();
        Engine engine = engineService.getByName(engineName);

        Set<Suspension> suspensions = suspensionService.getAll();
        System.out.println("\nТип, хід підвіски");
        for (Suspension suspension : suspensions) {
          System.out.printf("%s, %f%n", suspension.getType(), suspension.getSuspensionTravel());
        }
        System.out.println("Введіть новий тип підвіски: ");
        Scanner suspensionTypeInput = new Scanner(System.in, Charset.forName(
            System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251"
                : "UTF-8"));
        String suspensionType = suspensionTypeInput.nextLine();
        Suspension suspension = suspensionService.getByType(suspensionType);

        System.out.println("Введіть додатковий опис: ");
        Scanner descriptionInput = new Scanner(System.in, Charset.forName(
            System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251"
                : "UTF-8"));
        String description = descriptionInput.nextLine();

        Motorcycle motorcycleToUpdate = motorcycleService.getByName(userChoice);
        MotorcycleAddDto motorcycle = new MotorcycleAddDto(motorcycleToUpdate.getId(), name,
            engine,
            suspension, description);

        motorcycleService.remove(motorcycleToUpdate);
        motorcycleService.add(motorcycle);
        System.out.println("Дані успішно змінено");
      }
    } catch (EntityNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      System.out.println("Помилка, спробуйте ще раз");
    }
  }

  private void filterByEngine() {
    System.out.println("Введіть потрібну назву двигуна: ");
    Scanner engineNameInput = new Scanner(System.in);
    try {
      String engineName = engineNameInput.nextLine();
      Engine choicedEngine = engineService.getByName(engineName);
      Set<Motorcycle> motorcycles = motorcycleService.getByEngine(choicedEngine);
      int i = 1;
      for (Motorcycle motorcycle : motorcycles) {
        System.out.printf("%d. %s, %s, %s, %s%n", i, motorcycle.getName(),
            motorcycle.getEngine().getName(),
            motorcycle.getSuspension().getType(), motorcycle.getDescription());
        i++;
      }
    } catch (EntityNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      System.out.println("Помилка, спробуйте ще раз");
    }
  }

  private void filterBySuspension() {
    System.out.println("Введіть потрібний тип підвіски: ");
    Scanner suspensionTypeInput = new Scanner(System.in);
    try {
      String suspensionType = suspensionTypeInput.nextLine();
      Suspension choicedSuspension = suspensionService.getByType(suspensionType);
      Set<Motorcycle> motorcycles = motorcycleService.getBySuspension(choicedSuspension);
      int i = 1;
      for (Motorcycle motorcycle : motorcycles) {
        System.out.printf("%d. %s, %s, %s, %s%n", i, motorcycle.getName(),
            motorcycle.getEngine().getName(),
            motorcycle.getSuspension().getType(), motorcycle.getDescription());
        i++;
      }
    } catch (EntityNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      System.out.println("Помилка, спробуйте ще раз");
    }
  }

}
