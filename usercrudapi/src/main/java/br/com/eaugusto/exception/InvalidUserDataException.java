package br.com.eaugusto.exception;

/**
 * Exception thrown when provided data is invalid or incomplete.
 *
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since Nov 10, 2025
 */
public class InvalidUserDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidUserDataException(String message) {
		super(message);
	}
}
