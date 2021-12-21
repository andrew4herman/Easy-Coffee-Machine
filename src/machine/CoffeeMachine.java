package machine;

import java.util.Arrays;

public class CoffeeMachine {

    private CoffeeMachineState machineState;
    private int money;
    private int water;
    private int milk;
    private int coffeeBeans;
    private int cups;

    public CoffeeMachine() {
        machineState = CoffeeMachineState.TURNED_OFF;
        money = 550;
        water = 400;
        milk = 540;
        coffeeBeans = 120;
        cups = 9;
    }

    public void pressPowerButton() {
        machineState = machineState == CoffeeMachineState.TURNED_OFF ?
                CoffeeMachineState.CHOOSING_AN_ACTION :
                CoffeeMachineState.TURNED_OFF;

        showMessage(machineState.getMessage());
    }

    public void interact(String userOption) {
        try {
            switch (machineState) {
                case CHOOSING_AN_ACTION -> chooseAction(userOption);
                case CHOOSING_A_COFFEE -> chooseCoffee(userOption);
                case FILL -> {
                    int[] resources = Arrays.stream(userOption.split(" "))
                            .mapToInt(Integer::parseInt)
                            .filter(num -> num > 0)
                            .toArray();

                    if (resources.length != 4) {
                        throw new Exception();
                    } else {
                        fill(resources);
                    }
                }
            }
        } catch (Exception exception) {
            showMessage(exception.getMessage());
        }

        showMessage(machineState.getMessage());
    }

    private void chooseAction(String action) throws Exception {
        switch (action) {
            case "buy" -> machineState = CoffeeMachineState.CHOOSING_A_COFFEE;
            case "fill" -> machineState = CoffeeMachineState.FILL;
            case "remaining" -> displayRemaining();
            case "take" -> take();
            case "exit" -> {
                showMessage("Bye!");
                machineState = CoffeeMachineState.TURNED_OFF;
            }
            default -> throw new Exception("Incorrect action. Please try again.");
        }
    }

    private void chooseCoffee(String option) throws Exception {
        Coffee coffee;

        switch (option) {
            case "1" -> coffee = Coffee.ESPRESSO;
            case "2" -> coffee = Coffee.LATTE;
            case "3" -> coffee = Coffee.CAPPUCCINO;
            case "back" -> {
                machineState = CoffeeMachineState.CHOOSING_AN_ACTION;
                return;
            }
            default -> throw new Exception("Incorrect option. Choose coffee number or return back.");
        }

        checkIngredientsFor(coffee);
        makeCoffee(coffee);

        machineState = CoffeeMachineState.CHOOSING_AN_ACTION;
    }

    private void checkIngredientsFor(Coffee coffee) throws Exception {
        String error = "";

        if (coffee.getWater() > water) {
            error = "water";
        } else if (coffee.getMilk() > milk) {
            error = "milk";
        } else if (coffee.getCoffeeBeans() > coffeeBeans) {
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
        water -= coffee.getWater();
        milk -= coffee.getMilk();
        coffeeBeans -= coffee.getCoffeeBeans();
        cups--;
    }

    private void fill(int[] resources) {
            water += resources[0];
            milk += resources[1];
            coffeeBeans += resources[2];
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
                water, milk, coffeeBeans, cups, money)
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
