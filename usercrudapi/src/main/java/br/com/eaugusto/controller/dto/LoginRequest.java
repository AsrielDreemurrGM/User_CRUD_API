package br.com.eaugusto.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object representing the login request payload. Contains email
 * and password fields provided by the client. Used by AuthController for user
 * authentication.
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since Nov 11, 2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

	private String email;
	private String senha;
}
