package machine;

public class CoffeeMachine {

    private CoffeeMachineState machineState;
    private int money, water, milk, coffeeBeans, cups;

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
                case FILL -> fill(userOption);
            }
        } catch (IllegalArgumentException exception) {
            showMessage(exception.getMessage());
        }

        showMessage(machineState.getMessage());
    }

    private void chooseAction(String action) throws IllegalArgumentException {
        switch (action) {
            case "buy" -> machineState = CoffeeMachineState.CHOOSING_A_COFFEE;
            case "fill" -> machineState = CoffeeMachineState.FILL;
            case "remaining" -> displayRemaining();
            case "take" -> take();
            case "exit" -> {
                showMessage("Bye!");
                machineState = CoffeeMachineState.TURNED_OFF;
            }
            default -> throw new IllegalArgumentException("Incorrect action. Please try again.");
        }
    }

    private void chooseCoffee(String option) throws IllegalArgumentException {
        Coffee coffee;

        switch (option) {
            case "1":
                coffee = Coffee.ESPRESSO;
                break;
            case "2":
                coffee = Coffee.LATTE;
                break;
            case "3":
                coffee = Coffee.CAPPUCCINO;
                break;
            case "back":
                machineState = CoffeeMachineState.CHOOSING_AN_ACTION;
                return;
            default:
                throw new IllegalArgumentException("Incorrect option. Choose coffee number or return back.");
        }

        checkIngredientsFor(coffee);
        makeCoffee(coffee);

        machineState = CoffeeMachineState.CHOOSING_AN_ACTION;
    }

    private void checkIngredientsFor(Coffee coffee) throws IllegalArgumentException {
        String error = "";

        if (cups < 1) error = "cups";
        if (coffee.getCoffeeBeans() > coffeeBeans) error = "coffee beans";
        if (coffee.getMilk() > milk) error = "milk";
        if (coffee.getWater() > water) error = "water";

        if (!error.isEmpty())
            throw new IllegalArgumentException(String.format("Sorry, not enough %s!", error));
    }

    private void makeCoffee(Coffee coffee) {
        showMessage("I have enough resources, making you a coffee!");

        money += coffee.getPrice();
        water -= coffee.getWater();
        milk -= coffee.getMilk();
        coffeeBeans -= coffee.getCoffeeBeans();
        cups--;
    }

    private void fill(String ingredients) throws IllegalArgumentException {
        String[] resources = ingredients.split(" ");
        try {
            water += Integer.parseInt(resources[0]);
            milk += Integer.parseInt(resources[1]);
            coffeeBeans += Integer.parseInt(resources[2]);
            cups += Integer.parseInt(resources[3]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Incorrect input. Please enter correct amount of ingredients.");
        }

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
