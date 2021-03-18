package it.polito.dp2.TAMELESS.sol.db;

public class Neo4jClientException extends Exception {

	public Neo4jClientException() {
	}

	public Neo4jClientException(String message) {
		super(message);
	}

	public Neo4jClientException(Throwable cause) {
		super(cause);
	}

	public Neo4jClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public Neo4jClientException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
