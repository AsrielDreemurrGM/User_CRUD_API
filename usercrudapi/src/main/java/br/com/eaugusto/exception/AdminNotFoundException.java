package br.com.eaugusto.exception;

/**
 * Exception thrown when the initializer admin account is not found in the
 * database right after initialization.
 *
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since Nov 11, 2025
 */
public class AdminNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AdminNotFoundException(String message) {
		super(message);
	}

	public AdminNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
