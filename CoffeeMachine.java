//line 68: String text block -> version problem?

package machine;

import java.util.Arrays;
import java.util.Scanner;
import java.util.*;

public class CoffeeMachine {
    static int moneyCurrent = 550;
    static int waterCurrent = 400;
    static int milkCurrent = 540;
    static int coffeeBeansCurrent = 120;
    static int disposableCupsCurrent = 9;

    static String action = "";
    static String coffeeChoice = "";
    static int cupsOfCoffee = 0;

    static int waterToFill = 0;
    static int milkToFill = 0;
    static int coffeeBeansToFill = 0;
    static int disposableCupsToFill = 0;

    static int waterRequired = 0;
    static int milkRequired = 0;
    static int coffeeBeansRequired = 0;
    static int disposableCupsRequired = 0;
    static int moneyRequired = 0;
    static int[] minimum = new int[3];

    static boolean canProceed = false;

    enum Status {
        BUY, FILL, TAKE, REMAINING, EXIT
    }

    Status buy = Status.BUY;
    Status fill = Status.FILL;
    Status take = Status.TAKE;
    Status remaining = Status.REMAINING;
    Status exit = Status.EXIT;
    static Status status;

    public static void main(String[] args) {

        waterRequired = cupsOfCoffee * 200;
        milkRequired = cupsOfCoffee * 50;
        coffeeBeansRequired = cupsOfCoffee * 15;

        initializeQuestion();
        act();
        while (!status.equals(Status.valueOf("EXIT"))) {
            initializeQuestion();
            act();
        }
    }

    public static void initializeQuestion() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write action (buy, fill, take, remaining, exit): ");
        action = scanner.next();
        String actionUpper = action.toUpperCase();
        status = Status.valueOf(actionUpper);
    }

    public static void checkInventory() {
        String inventory = """
                The coffee machine has:
                %d ml of water
                %d ml of milk
                %d g of coffee beans
                %d disposable cups
                $%d of money""";

        System.out.println(inventory.formatted(waterCurrent, milkCurrent, coffeeBeansCurrent, disposableCupsCurrent, moneyCurrent));
    }

    public static void updateInventory() {
        if (status.equals(Status.valueOf("BUY")) && canProceed) {
            waterCurrent -= waterRequired;
            milkCurrent -= milkRequired;
            coffeeBeansCurrent -= coffeeBeansRequired;
            moneyCurrent += moneyRequired;
            disposableCupsCurrent -= disposableCupsRequired;
        } else if (status.equals(Status.valueOf("FILL"))) {
            waterCurrent += waterToFill;
            milkCurrent += milkToFill;
            coffeeBeansCurrent += coffeeBeansToFill;
            disposableCupsCurrent += disposableCupsToFill;
        } else if (status.equals(Status.valueOf("TAKE"))) {
            moneyCurrent = 0;
        }
    }

    public static void act() {
        switch (status) {
            case BUY:
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino: ");
                Scanner scanner3 = new Scanner(System.in);
                coffeeChoice = scanner3.next();
                inputInventoryNeeded();
                printAvailability();
                updateInventory();
                break;
            case FILL:
                Scanner scanner2 = new Scanner(System.in);
                System.out.println("Write how many ml of water you want to add: ");
                waterToFill = scanner2.nextInt();
                System.out.println("Write how many ml of milk you want to add: ");
                milkToFill = scanner2.nextInt();
                System.out.println("Write how many grams of coffee beans you want to add: ");
                coffeeBeansToFill = scanner2.nextInt();
                System.out.println("Write how many disposable cups of coffee you want to add: ");
                disposableCupsToFill = scanner2.nextInt();
                updateInventory();
                break;
            case TAKE:
                System.out.println("I gave you $%d".formatted(moneyCurrent));
                updateInventory();
                break;
            case REMAINING:
                checkInventory();
            case EXIT:
                break;
        }
    }

    public static void checkQuantity() {
            minimum[0] = waterCurrent / 200;
            minimum[1] = milkCurrent / 50;
            minimum[2] = coffeeBeansCurrent / 15;
            int min = minimum[0];

            for (int i = 0; i < minimum.length; i++) {
                if (minimum[i] < min) {
                    min = minimum[i];
                }
            }

            if (min == cupsOfCoffee) {
                System.out.println("Yes, I can make that amount of coffee");
            } else if (min > cupsOfCoffee) {
                System.out.println("Yes, I can make that amount of coffee (and even %d more than that)".formatted(min-1));
            } else if (min < cupsOfCoffee) {
                System.out.println("No, I can make only %d cup(s) of coffee".formatted(min));
            }
    }

    public static void inputInventoryNeeded() {
        switch (coffeeChoice) {
            case "1" -> {
                waterRequired = 250;
                coffeeBeansRequired = 16;
                moneyRequired = 4;
                disposableCupsRequired = 1;
            }
            case "2" -> {
                waterRequired = 350;
                milkRequired = 75;
                coffeeBeansRequired = 20;
                moneyRequired = 7;
                disposableCupsRequired = 1;
            }
            case "3" -> {
                waterRequired = 200;
                milkRequired = 100;
                coffeeBeansRequired = 12;
                moneyRequired = 6;
                disposableCupsRequired = 1;
            }
            case "back" -> buy();
        }
    }

    public static void buy() {
        initializeQuestion();
        act();
    }

    public static void printAvailability() {
        if (waterCurrent >= waterRequired && milkCurrent >= milkRequired && coffeeBeansCurrent >= coffeeBeansRequired && disposableCupsCurrent >= disposableCupsRequired) {
            System.out.println("I have enough resources, making you a coffee!");
            canProceed = true;
        } else if (waterCurrent < waterRequired) {
            System.out.println("Sorry, not enough water!");
            canProceed = false;
        } else if (milkCurrent < milkRequired) {
            System.out.println("Sorry, not enough milk!");
            canProceed = false;
        } else if (coffeeBeansCurrent < coffeeBeansRequired) {
            System.out.println("Sorry, not enough coffee bean!");
            canProceed = false;
        } else if (disposableCupsCurrent < disposableCupsRequired) {
            System.out.println("Sorry, not enough disposable cup!");
            canProceed = false;
        }
    }
}
