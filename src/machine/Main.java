package machine;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CoffeeMachine coffeeMachine = new CoffeeMachine();

        coffeeMachine.pressPowerButton();

        do {
            coffeeMachine.interact(scanner.nextLine());
        } while (coffeeMachine.getMachineState() != CoffeeMachineState.TURNED_OFF);

        scanner.close();
    }
}
