package br.com.eaugusto.exception;

/**
 * Thrown when JWT key generation fails.
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since Nov 11, 2025
 */
public class JWTKeyGenerationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public JWTKeyGenerationException(String message) {
		super(message);
	}

	public JWTKeyGenerationException(String message, Throwable cause) {
		super(message, cause);
	}
}
