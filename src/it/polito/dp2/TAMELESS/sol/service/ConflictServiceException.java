package it.polito.dp2.TAMELESS.sol.service;

public class ConflictServiceException extends Exception {

	public ConflictServiceException() {
	}

	public ConflictServiceException(String message) {
		super(message);
	}

	public ConflictServiceException(Throwable cause) {
		super(cause);
	}

	public ConflictServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConflictServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
