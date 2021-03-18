package it.polito.dp2.TAMELESS.ass;

public class DestroyedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DestroyedException() {
	}

	public DestroyedException(String message) {
		super(message);
	}

	public DestroyedException(Throwable cause) {
		super(cause);
	}

	public DestroyedException(String message, Throwable cause) {
		super(message, cause);
	}

	public DestroyedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
