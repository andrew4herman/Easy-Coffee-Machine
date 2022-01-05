package machine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CoffeeMachine {

    private final Map<Coffee, Integer> priceList;
    private CoffeeMachineState machineState;

    private final ResourcesContainer container;
    private int money;

    public CoffeeMachine() {
        priceList = new HashMap<>();
        machineState = CoffeeMachineState.TURNED_OFF;

        container = new ResourcesContainer(400, 540, 120, 9);
        money = 550;

        setPriceList();
    }

    private void setPriceList() {
        priceList.put(Coffee.ESPRESSO, 4);
        priceList.put(Coffee.LATTE, 7);
        priceList.put(Coffee.CAPPUCCINO, 6);
    }

    public void pressPowerButton() {
        machineState = machineState == CoffeeMachineState.TURNED_OFF ?
                CoffeeMachineState.CHOOSING_AN_ACTION :
                CoffeeMachineState.TURNED_OFF;

        showMessage(machineState.getMessage());
    }

    public void interact(String userOption) {
        switch (machineState) {
            case CHOOSING_AN_ACTION -> chooseActionOp(userOption);
            case CHOOSING_A_COFFEE -> chooseCoffeeOp(userOption);
            case FILL -> fillOp(userOption);
        }

        showMessage(machineState.getMessage());
    }

    private void fillOp(String userOption) {
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

    private void chooseCoffeeOp(String userOption) {
        if ("back".equals(userOption)) {
            machineState = CoffeeMachineState.CHOOSING_AN_ACTION;
        } else {
            Optional<Coffee> coffee = chooseCoffee(userOption);

            if (coffee.isEmpty()) {
                showMessage("Incorrect option. Choose coffee number or return back.");
            } else {
                makeCoffeeIfEnough(coffee.get());
            }
        }

        chooseCoffee(userOption);
    }

    private void chooseActionOp(String action) {
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

    private Optional<Coffee> chooseCoffee(String option) {
        return switch (option) {
            case "1" -> Optional.of(Coffee.ESPRESSO);
            case "2" -> Optional.of(Coffee.LATTE);
            case "3" -> Optional.of(Coffee.CAPPUCCINO);
            default -> Optional.empty();
        };
    }

    private void makeCoffeeIfEnough(Coffee coffee) {
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

        if (coffee.getMlOfWater() > container.getMlOfWater()) {
            error = "water";
        } else if (coffee.getMlOfMilk() > container.getMlOfMilk()) {
            error = "milk";
        } else if (coffee.getGrOfCoffeeBeans() > container.getGrOfCoffeeBeans()) {
            error = "coffee beans";
        } else if (container.getCups() < 1) {
            error = "cups";
        }

        if (!error.isEmpty()) {
            throw new Exception(String.format("Sorry, not enough %s!", error));
        }
    }

    private void makeCoffee(Coffee coffee) {
        showMessage("I have enough resources, making you a coffee!");

        money += priceList.get(coffee);
        container.useResources(
                coffee.getMlOfWater(),
                coffee.getMlOfMilk(),
                coffee.getGrOfCoffeeBeans(),
                1
        );

    }

    private void fill(int[] resources) {
        container.addResources(
                resources[0], // Water
                resources[1], // Milk
                resources[2], // Coffee beans
                resources[3]  // Cups
        );

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
                container.getMlOfWater(),
                container.getMlOfMilk(),
                container.getGrOfCoffeeBeans(),
                container.getCups(),
                money)
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
