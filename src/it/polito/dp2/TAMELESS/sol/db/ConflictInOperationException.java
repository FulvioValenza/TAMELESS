package it.polito.dp2.TAMELESS.sol.db;

public class ConflictInOperationException extends Exception {

	public ConflictInOperationException() {
	}

	public ConflictInOperationException(String message) {
		super(message);
	}

	public ConflictInOperationException(Throwable cause) {
		super(cause);
	}

	public ConflictInOperationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConflictInOperationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
