package ampa.sa.util.exceptions;

@SuppressWarnings("serial")
public class MaxCapacityException extends InstanceException {

    public MaxCapacityException(Object key, String className) {
        super("Max Capacity Exception", key, className);
    }
    
}
