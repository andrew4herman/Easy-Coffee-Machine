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

    public void useResources(int mlOfWater, int mlOfMilk, int grOfCoffeeBeans, int cups)
            throws NotEnoughResourcesException {
        String error = "";

        if (mlOfWater > this.getMlOfWater()) {
            error = "water";
        } else if (mlOfMilk > this.getMlOfMilk()) {
            error = "milk";
        } else if (grOfCoffeeBeans > this.getGrOfCoffeeBeans()) {
            error = "coffee beans";
        } else if (cups > this.cups) {
            error = "cups";
        }

        if (!error.isEmpty()) {
            throw new NotEnoughResourcesException(String.format("Sorry, not enough %s!", error));
        } else {
            addResources(-mlOfWater, -mlOfMilk, -grOfCoffeeBeans, -cups);
        }
    }
}
