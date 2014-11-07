package ampa.sa.util.exceptions;

@SuppressWarnings("serial")
public class ReceiptsNotFoundException extends InstanceException {

	public ReceiptsNotFoundException(Object key, String className) {
		super("Receipt not found", key, className);
	}

}
