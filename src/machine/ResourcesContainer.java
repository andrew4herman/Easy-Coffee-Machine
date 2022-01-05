package machine;

public class ResourcesContainer {

    private int mlOfWater;
    private int mlOfMilk;
    private int grOfCoffeeBeans;
    private int cups;

    public ResourcesContainer(int mlOfWater, int mlOfMilk, int grOfCoffeeBeans, int cups) {
        this.mlOfWater = mlOfWater;
        this.mlOfMilk = mlOfMilk;
        this.grOfCoffeeBeans = grOfCoffeeBeans;
        this.cups = cups;
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

    public int getCups() {
        return cups;
    }

    public void addResources(int mlOfWater, int mlOfMilk, int grOfCoffeeBeans, int cups) {
        this.mlOfWater += mlOfWater;
        this.mlOfMilk += mlOfMilk;
        this.grOfCoffeeBeans += grOfCoffeeBeans;
        this.cups += cups;
    }

    public void useResources(int mlOfWater, int mlOfMilk, int grOfCoffeeBeans, int cups) {
        addResources(-mlOfWater, -mlOfMilk, -grOfCoffeeBeans, -cups);
    }
}
