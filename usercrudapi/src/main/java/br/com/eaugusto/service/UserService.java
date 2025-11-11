package br.com.eaugusto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.eaugusto.exception.InvalidUserDataException;
import br.com.eaugusto.exception.UserNotFoundException;
import br.com.eaugusto.model.User;
import br.com.eaugusto.repository.IUserRepository;

/**
 * Service layer implementation for managing User entities. Handles CRUD
 * operations by delegating to the repository. Uses BCrypt PasswordEncoder for
 * encryption.
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since Nov 10, 2025
 */
@Service
public class UserService implements IUserService {

	private final IUserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(@NonNull IUserRepository userRepository, @NonNull PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User saveUser(@NonNull User user) {
		validateUserData(user);
		user.setSenha(passwordEncoder.encode(user.getSenha()));
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> getUserById(@NonNull Long id) {
		return userRepository.findById(id);
	}

	@Override
	public User updateUser(@NonNull Long id, @NonNull User updatedUser) {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com o ID: " + id));

		validateUserData(updatedUser);

		existingUser.setNome(updatedUser.getNome());
		existingUser.setEmail(updatedUser.getEmail());
		existingUser.setSenha(passwordEncoder.encode(updatedUser.getSenha()));

		return userRepository.save(existingUser);
	}

	@Override
	public void deleteUser(@NonNull Long id) {
		if (!userRepository.existsById(id)) {
			throw new UserNotFoundException("Usuário não encontrado com o ID: " + id);
		}

		userRepository.deleteById(id);
	}

	/**
	 * Validates user data for required fields before persistence.
	 * 
	 * @param user The user object to validate.
	 * @throws InvalidUserDataException if any required field is null or blank.
	 */
	private void validateUserData(@NonNull User user) {
		if (user.getNome() == null || user.getNome().isBlank()) {
			throw new InvalidUserDataException("O nome do usuário não pode estar vazio.");
		}
		if (user.getEmail() == null || user.getEmail().isBlank()) {
			throw new InvalidUserDataException("O e-mail do usuário não pode estar vazio.");
		}
		if (user.getSenha() == null || user.getSenha().isBlank()) {
			throw new InvalidUserDataException("A senha do usuário não pode estar vazia.");
		}
	}
}
