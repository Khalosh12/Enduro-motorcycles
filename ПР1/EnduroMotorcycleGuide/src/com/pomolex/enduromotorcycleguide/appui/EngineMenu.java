package com.pomolex.enduromotorcycleguide.appui;

import com.pomolex.enduromotorcycleguide.domain.contract.EngineService;
import com.pomolex.enduromotorcycleguide.domain.dto.EngineAddDto;
import com.pomolex.enduromotorcycleguide.domain.exception.EntityNotFoundException;
import com.pomolex.enduromotorcycleguide.persistence.entity.impl.Engine;
import com.pomolex.enduromotorcycleguide.persistence.repository.RepositoryFactory;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

public class EngineMenu {

  private final EngineService engineService;

  public EngineMenu(EngineService engineService) {
    this.engineService = engineService;
  }

  /**
   * Displays engine menu.
   */
  public void engineMenu() {
    System.out.println("Оберіть потрібний пункт");
    while (true) {
      System.out.println("1. Переглянути \n2. Пошук \n3. Додати \n4. Видалити \n5. Редагувати "
          + "\n6. Фільтрувати по місткості \n7. Вихід");
      System.out.println("Оберіть потрібний пункт меню: ");
      Scanner userInput = new Scanner(System.in);
      try {
        RepositoryFactory jsonRepositoryFactory = RepositoryFactory
            .getRepositoryFactory(RepositoryFactory.JSON);

        int userChoice = userInput.nextInt();
        switch (userChoice) {
          case 1:
            viewEngines();
            break;
          case 2:
            searchEngine();
            break;
          case 3:
            addEngine();
            jsonRepositoryFactory.commit();
            break;
          case 4:
            deleteEngine();
            jsonRepositoryFactory.commit();
            break;
          case 5:
            updateEngine();
            jsonRepositoryFactory.commit();
            break;
          case 6:
            viewFilteredEngines();
            break;
          case 7:
            return;
          default:
            System.out.println("Помилка, введіть числа 1 або 7");
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

  private void viewEngines() {
    Set<Engine> engines = engineService.getAll();
    if (!engines.isEmpty()) {
      int i = 1;
      System.out.println("Номер, назва, місткість, чи двотактний");
      for (Engine engine : engines) {
        System.out.printf("%d. %s, %d, %s%n", i, engine.getName(), engine.getCapacity(),
            engine.isTwoStroke() ? "Так" : "Ні");
        i++;
      }
    } else {
      System.out.println("Список двигунів пустий");
    }
  }

  private void searchEngine() {
    System.out.println("Введіть назву: ");
    Scanner engineNameInput = new Scanner(System.in, Charset.forName(
        System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251"
            : "UTF-8"));
    try {
      String engineName = engineNameInput.nextLine();
      Engine engine = engineService.getByName(engineName);
      System.out.println("Назва, місткість, чи двотактний");
      System.out.printf("%s, %d, %s%n", engine.getName(), engine.getCapacity(),
          engine.isTwoStroke() ? "Так" : "Ні");
    } catch (EntityNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      System.out.println("Помилка, спробуйте ще раз");
    }
  }

  private void addEngine() {
    System.out.print("Введіть назву: ");
    try {
      Scanner engineInput = new Scanner(System.in, Charset.forName(
          System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251"
              : "UTF-8"));

      String engineName = engineInput.nextLine();
      System.out.print("Введіть місткість: ");
      Scanner capacityInput = new Scanner(System.in);
      int capacity = capacityInput.nextInt();
      System.out.print("Цей двигун двотактний(Так/Ні): ");
      Scanner isTwoStrokeInput = new Scanner(System.in);

      boolean isTwoStroke = isTwoStrokeInput.nextLine().equals("Так");
      EngineAddDto engine = new EngineAddDto(UUID.randomUUID(), engineName, capacity,
          isTwoStroke);
      if (engineService.getAll(e -> e.getName().equals(engineName)).isEmpty()) {
        engineService.add(engine);
        System.out.println("Дані успішно додано");
      } else {
        System.out.println("Такий двигун вже існує");
      }
    } catch (EntityNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      System.out.println("Помилка, спробуйте ще раз");
    }
  }

  private void deleteEngine() {
    viewEngines();
    System.out.print("Введіть назву двигуна: ");
    Scanner userInput = new Scanner(System.in, Charset.forName(
        System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251"
            : "UTF-8"));
    try {
      String userChoice = userInput.nextLine();
      Engine deletedEngine = engineService.getByName(userChoice);
      engineService.remove(deletedEngine);
      System.out.println("Дані успішно видалено");
    } catch (Exception e) {
      System.out.println("Помилка, спробуйте ще раз");
    }
  }

  private void updateEngine() {
    viewEngines();
    try {
      System.out.print("Введіть назву двигуна: ");
      Scanner userInput = new Scanner(System.in, Charset.forName(
          System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251"
              : "UTF-8"));
      String userChoice = userInput.nextLine();
      if (engineService.getAll(engine -> engine.getName().equals(userChoice)).isEmpty()) {
        System.out.println("Такого двигуну не існує");
        return;
      }
      System.out.print("Введіть нову назву: ");
      Scanner engineNameInput = new Scanner(System.in, Charset.forName(
          System.getProperty("os.name").toLowerCase().startsWith("win") ? "Windows-1251"
              : "UTF-8"));
      String engineName = engineNameInput.nextLine();

      System.out.print("Введіть нову місткість: ");
      Scanner capacityInput = new Scanner(System.in);
      int capacity = capacityInput.nextInt();

      System.out.print("Цей двигун двотактний(Так/Ні): ");
      Scanner isTwoStrokeInput = new Scanner(System.in);
      boolean isTwoStroke = isTwoStrokeInput.nextLine().equals("Так");

      Engine engineToUpdate = engineService.getByName(userChoice);
      EngineAddDto engine = new EngineAddDto(engineToUpdate.getId(), engineName, capacity,
          isTwoStroke);

      engineService.remove(engineToUpdate);
      engineService.add(engine);
      System.out.println("Дані успішно змінено");
    } catch (EntityNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      System.out.println("Помилка, спробуйте ще раз");
    }
  }

  private void viewFilteredEngines() {
    System.out.println("Введіть потрібну місткість: ");
    Scanner capacityInput = new Scanner(System.in);
    try {
      int capacity = capacityInput.nextInt();
      Set<Engine> engines = engineService.getByCapacity(capacity);
      if (!engines.isEmpty()) {
        int i = 1;
        System.out.println("Номер, назва, місткість, чи двотактний");
        for (Engine engine : engines) {
          System.out.printf("%d. %s, %d, %s%n", i, engine.getName(), engine.getCapacity(),
              engine.isTwoStroke() ? "Так" : "Ні");
          i++;
        }
      } else {
        System.out.println("Таких двигунів не існує");
      }
    } catch (Exception e) {
      System.out.println("Помилка, спробуйте ще раз");
    }
  }

}
