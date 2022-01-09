package machine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CoffeeMachine {

    private final ResourcesContainer container;
    private CoffeeMachineState machineState;

    private final Map<Coffee, Integer> priceList;
    private int money;

    public CoffeeMachine() {
        container = new ResourcesContainer(400, 540, 120, 9);
        machineState = CoffeeMachineState.TURNED_OFF;

        priceList = new HashMap<>();
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
            case CHOOSING_AN_ACTION -> makeAction(userOption);
            case CHOOSING_A_COFFEE -> chooseCoffeeProcess(userOption);
            case FILL -> fillProcess(userOption);
        }

        showMessage(machineState.getMessage());
    }

    private void fillProcess(String userOption) {
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

    private void chooseCoffeeProcess(String userOption) {
        if ("back".equals(userOption)) {
            machineState = CoffeeMachineState.CHOOSING_AN_ACTION;
        } else {
            Coffee coffee = chooseCoffee(userOption);

            if (coffee == Coffee.NONE) {
                showMessage("Incorrect option. Choose coffee number or return back.");
            } else {
                tryToMakeCoffee(coffee);
            }
        }
    }

    private void makeAction(String action) {
        switch (action) {
            case "buy" -> machineState = CoffeeMachineState.CHOOSING_A_COFFEE;
            case "fill" -> machineState = CoffeeMachineState.FILL;
            case "remaining" -> displayRemaining();
            case "take" -> take();
            case "exit" -> exit();
            default -> showMessage("Incorrect action. Please try again.");
        }
    }

    private Coffee chooseCoffee(String option) {
        return switch (option) {
            case "1" -> Coffee.ESPRESSO;
            case "2" -> Coffee.LATTE;
            case "3" -> Coffee.CAPPUCCINO;
            default -> Coffee.NONE;
        };
    }

    private void tryToMakeCoffee(Coffee coffee) {
        try {
            takeIngredientsFor(coffee);
            money += priceList.get(coffee);

            showMessage("I have enough resources, making you a coffee!");
            machineState = CoffeeMachineState.CHOOSING_AN_ACTION;
        } catch (NotEnoughResourcesException e) {
            System.out.println(e.getMessage());
        }
    }

    private void takeIngredientsFor(Coffee coffee) throws NotEnoughResourcesException {
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

    private void exit() {
        showMessage("Bye!");
        machineState = CoffeeMachineState.TURNED_OFF;
    }

    private void showMessage(String message) {
        System.out.printf("\n%s\n", message);
    }

    public CoffeeMachineState getMachineState() {
        return machineState;
    }
}
