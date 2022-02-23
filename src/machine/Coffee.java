package machine;

/**
 * Simple enum class that represents the different types of coffee.
 */
public enum Coffee {

    ESPRESSO(250, 0, 16),
    LATTE(350, 75, 20),
    CAPPUCCINO(200, 100, 12),
    NONE(0, 0, 0);

    private final int mlOfWater;
    private final int mlOfMilk;
    private final int grOfCoffeeBeans;

    Coffee(int mlOfWater, int mlOfMilk, int grOfCoffeeBeans) {
        this.mlOfWater = mlOfWater;
        this.mlOfMilk = mlOfMilk;
        this.grOfCoffeeBeans = grOfCoffeeBeans;
    }

    public int getMlOfWater() {
        return mlOfWater;
    }

    public int getMlOfMilk() {
        return mlOfMilk;
    }

    public int getGrOfCoffeeBeans() {
        return grOfCoffeeBeans;
    }

}
