package machine;

public class NotEnoughResourcesException extends Exception {

    public NotEnoughResourcesException(String message) {
        super(message);
    }
}
