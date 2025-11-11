package br.com.eaugusto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;

import br.com.eaugusto.model.User;

/**
 * Defines operations for managing users in the system.
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since Nov 10, 2025
 */
public interface IUserService {

	User saveUser(@NonNull User user);

	List<User> getAllUsers();

	Optional<User> getUserById(@NonNull Long id);

	User updateUser(@NonNull Long id, @NonNull User updatedUser);

	void deleteUser(@NonNull Long id);
}
