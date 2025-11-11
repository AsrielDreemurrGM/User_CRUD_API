package br.com.eaugusto.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object representing a user creation or update request. Contains
 * name, email, and password fields sent by the client. Used for user management
 * operations in UserController.
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since Nov 11, 2025
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

	private String nome;
	private String email;
	private String senha;
}
