package machine;

public enum Coffee {

    ESPRESSO(250, 0, 16, 4),
    LATTE(350, 75, 20, 7),
    CAPPUCCINO(200, 100, 12, 6);

    private final int mlOfWater;
    private final int mlOfMilk;
    private final int grOfCoffeeBeans;
    private final int price;

    Coffee(int mlOfWater, int mlOfMilk, int grOfCoffeeBeans, int price) {
        this.mlOfWater = mlOfWater;
        this.mlOfMilk = mlOfMilk;
        this.grOfCoffeeBeans = grOfCoffeeBeans;
        this.price = price;
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

    public int getPrice() {
        return price;
    }
}
