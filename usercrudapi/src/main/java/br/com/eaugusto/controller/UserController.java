package br.com.eaugusto.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.eaugusto.controller.dto.UserRequest;
import br.com.eaugusto.controller.dto.UserResponse;
import br.com.eaugusto.exception.InvalidUserDataException;
import br.com.eaugusto.exception.UserNotFoundException;
import br.com.eaugusto.model.User;
import br.com.eaugusto.service.IUserService;

/**
 * REST controller responsible for handling user-related HTTP requests. Provides
 * endpoints for CRUD operations with validation and error handling.
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since Nov 11, 2025
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

	private final IUserService userService;

	public UserController(IUserService userService) {
		if (userService == null) {
			throw new IllegalArgumentException("UserService cannot be null.");
		}
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<List<UserResponse>> getAll() {
		List<UserResponse> result = userService.getAllUsers().stream().map(UserController::toResponse).toList();

		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
		if (id == null) {
			throw new InvalidUserDataException("O ID do usuário não pode ser nulo.");
		}

		User user = userService.getUserById(id)
				.orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com ID: " + id));

		return ResponseEntity.ok(toResponse(user));
	}

	@PostMapping
	public ResponseEntity<UserResponse> create(@RequestBody UserRequest request) {
		if (request == null) {
			throw new InvalidUserDataException("A requisição do usuário não pode ser nula.");
		}

		User toSave = toEntity(request);
		User saved = userService.saveUser(toSave);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.getId())
				.toUri();

		return ResponseEntity.created(location).body(toResponse(saved));
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UserRequest request) {
		if (id == null) {
			throw new InvalidUserDataException("O ID do usuário não pode ser nulo.");
		}
		if (request == null) {
			throw new InvalidUserDataException("A requisição do usuário não pode ser nula.");
		}

		User toUpdate = toEntity(request);
		User updated = userService.updateUser(id, toUpdate);

		return ResponseEntity.ok(toResponse(updated));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (id == null) {
			throw new InvalidUserDataException("O ID do usuário não pode ser nulo.");
		}

		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

	private static UserResponse toResponse(User user) {
		return new UserResponse(user.getId(), user.getNome(), user.getEmail());
	}

	private static User toEntity(UserRequest request) {
		User user = new User();
		user.setNome(request.getNome());
		user.setEmail(request.getEmail());
		user.setSenha(request.getSenha());
		return user;
	}
}
