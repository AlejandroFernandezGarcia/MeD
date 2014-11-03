package ampa.sa.util.exceptions;

@SuppressWarnings("serial")
public class NotValidDateException extends InstanceException {

    public NotValidDateException(Object key, String className) {
        super("Holiday Day Exception", key, className);
    }
    
}
