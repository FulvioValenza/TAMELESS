package it.polito.dp2.TAMELESS.sol.db;

public class BadRequestInOperationException extends Exception {

	public BadRequestInOperationException() {
	}

	public BadRequestInOperationException(String message) {
		super(message);
	}

	public BadRequestInOperationException(Throwable cause) {
		super(cause);
	}

	public BadRequestInOperationException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadRequestInOperationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
