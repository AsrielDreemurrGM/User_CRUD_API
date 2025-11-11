package br.com.eaugusto.service;

import java.util.List;
import java.util.Optional;

import br.com.eaugusto.model.User;

/**
 * Defines operations for managing users in the system.
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since Nov 10, 2025
 */
public interface IUserService {

	User saveUser(User user);

	List<User> getAllUsers();

	Optional<User> getUserById(Long id);

	User updateUser(Long id, User updatedUser);

	void deleteUser(Long id);
}
