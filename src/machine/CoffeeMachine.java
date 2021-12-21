package machine;

import java.util.Arrays;

public class CoffeeMachine {

    private CoffeeMachineState machineState;
    private int money;
    private int mlOfWater;
    private int mlOfMilk;
    private int grOfCoffeeBeans;
    private int cups;

    public CoffeeMachine() {
        machineState = CoffeeMachineState.TURNED_OFF;
        money = 550;
        mlOfWater = 400;
        mlOfMilk = 540;
        grOfCoffeeBeans = 120;
        cups = 9;
    }

    public void pressPowerButton() {
        machineState = machineState == CoffeeMachineState.TURNED_OFF ?
                CoffeeMachineState.CHOOSING_AN_ACTION :
                CoffeeMachineState.TURNED_OFF;

        showMessage(machineState.getMessage());
    }

    public void interact(String userOption) {
        switch (machineState) {
            case CHOOSING_AN_ACTION -> chooseAction(userOption);
            case CHOOSING_A_COFFEE -> chooseCoffee(userOption);
            case FILL -> {
                int[] resources = Arrays.stream(userOption.split(" "))
                        .mapToInt(Integer::parseInt)
                        .filter(num -> num > 0)
                        .toArray();

                if (resources.length != 4) {
                    showMessage("Incorrect input. Please enter correct amount of ingredients.");
                } else {
                    fill(resources);
                }
            }
        }

        showMessage(machineState.getMessage());
    }

    private void chooseAction(String action) {
        switch (action) {
            case "buy" -> machineState = CoffeeMachineState.CHOOSING_A_COFFEE;
            case "fill" -> machineState = CoffeeMachineState.FILL;
            case "remaining" -> displayRemaining();
            case "take" -> take();
            case "exit" -> {
                showMessage("Bye!");
                machineState = CoffeeMachineState.TURNED_OFF;
            }
            default -> showMessage("Incorrect action. Please try again.");
        }
    }

    private void chooseCoffee(String option) {
        Coffee coffee;

        switch (option) {
            case "1" -> coffee = Coffee.ESPRESSO;
            case "2" -> coffee = Coffee.LATTE;
            case "3" -> coffee = Coffee.CAPPUCCINO;
            case "back" -> {
                machineState = CoffeeMachineState.CHOOSING_AN_ACTION;
                return;
            }
            default -> {
                showMessage("Incorrect option. Choose coffee number or return back.");
                return;
            }
        }

        try {
            checkIngredientsFor(coffee);
            makeCoffee(coffee);

            machineState = CoffeeMachineState.CHOOSING_AN_ACTION;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void checkIngredientsFor(Coffee coffee) throws Exception {
        String error = "";

        if (coffee.getMlOfWater() > mlOfWater) {
            error = "water";
        } else if (coffee.getMlOfMilk() > mlOfMilk) {
            error = "milk";
        } else if (coffee.getGrOfCoffeeBeans() > grOfCoffeeBeans) {
            error = "coffee beans";
        } else if (cups < 1) {
            error = "cups";
        }

        if (!error.isEmpty()) {
            throw new Exception(String.format("Sorry, not enough %s!", error));
        }
    }

    private void makeCoffee(Coffee coffee) {
        showMessage("I have enough resources, making you a coffee!");

        money += coffee.getPrice();
        mlOfWater -= coffee.getMlOfWater();
        mlOfMilk -= coffee.getMlOfMilk();
        grOfCoffeeBeans -= coffee.getGrOfCoffeeBeans();
        cups--;
    }

    private void fill(int[] resources) {
        mlOfWater += resources[0];
        mlOfMilk += resources[1];
        grOfCoffeeBeans += resources[2];
        cups += resources[3];

        machineState = CoffeeMachineState.CHOOSING_AN_ACTION;
    }

    private void displayRemaining() {
        showMessage(String.format("""
                        The coffee machine has:
                        %d ml of water
                        %d ml of milk
                        %d g of coffee beans
                        %d disposable cups
                        $%d of money""",
                mlOfWater, mlOfMilk, grOfCoffeeBeans, cups, money)
        );
    }

    private void take() {
        showMessage(String.format("I gave you $%d", money));
        money = 0;
    }

    private void showMessage(String message) {
        System.out.printf("\n%s\n", message);
    }

    public CoffeeMachineState getMachineState() {
        return machineState;
    }
}
