package br.com.eaugusto.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for managing application-wide exceptions. Returns
 * structured JSON responses for known error types to be shown in the front-end.
 *
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since Nov 10, 2025
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final String TIMESTAMP = "timestamp";
	private static final String STATUS = "status";
	private static final String ERROR = "error";
	private static final String MESSAGE = "message";

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException exception) {
		Map<String, Object> error = new HashMap<>();
		error.put(TIMESTAMP, LocalDateTime.now());
		error.put(STATUS, HttpStatus.NOT_FOUND.value());
		error.put(ERROR, "Usuário não encontrado.");
		error.put(MESSAGE, exception.getMessage());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidUserDataException.class)
	public ResponseEntity<Map<String, Object>> handleInvalidUserDataException(InvalidUserDataException exception) {
		Map<String, Object> error = new HashMap<>();
		error.put(TIMESTAMP, LocalDateTime.now());
		error.put(STATUS, HttpStatus.BAD_REQUEST.value());
		error.put(ERROR, "Dados de usuário inválidos.");
		error.put(MESSAGE, exception.getMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGenericException(Exception exception) {
		Map<String, Object> error = new HashMap<>();
		error.put(TIMESTAMP, LocalDateTime.now());
		error.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.put(ERROR, "Erro interno do servidor.");
		error.put(MESSAGE, exception.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
