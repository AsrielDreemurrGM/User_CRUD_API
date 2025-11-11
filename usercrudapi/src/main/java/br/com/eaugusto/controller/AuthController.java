package br.com.eaugusto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eaugusto.controller.dto.LoginRequest;
import br.com.eaugusto.exception.InvalidUserDataException;
import br.com.eaugusto.exception.UserNotFoundException;
import br.com.eaugusto.model.User;
import br.com.eaugusto.security.JWTUtil;
import br.com.eaugusto.service.IUserService;

/**
 * REST controller responsible for authentication operations. Handles login
 * requests by verifying user credentials and generating JWT tokens.
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since Nov 11, 2025
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private IUserService userService;
	private PasswordEncoder passwordEncoder;
	private JWTUtil jwtUtil;

	AuthController(IUserService userService, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest request) {
		User user = userService.getAllUsers().stream()
				.filter(eachUser -> eachUser.getEmail().equals(request.getEmail())).findFirst()
				.orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));

		if (!passwordEncoder.matches(request.getSenha(), user.getSenha())) {
			throw new InvalidUserDataException("Senha inválida");
		}

		String token = jwtUtil.generateToken(user);
		return ResponseEntity.ok(token);
	}
}
