package ampa.sa.util.exceptions;

@SuppressWarnings("serial")
public class BillNotFoundException extends InstanceException {

	public BillNotFoundException(Object key, String className) {
		super("Bill not found", key, className);
	}

}
