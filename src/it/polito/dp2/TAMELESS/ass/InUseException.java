package it.polito.dp2.TAMELESS.ass;

public class InUseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InUseException() {
	}

	public InUseException(String message) {
		super(message);
	}

	public InUseException(Throwable cause) {
		super(cause);
	}

	public InUseException(String message, Throwable cause) {
		super(message, cause);
	}

	public InUseException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
