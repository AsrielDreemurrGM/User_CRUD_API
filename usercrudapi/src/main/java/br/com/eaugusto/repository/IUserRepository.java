package br.com.eaugusto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.eaugusto.model.User;

/**
 * Repository interface for managing User entities. Provides CRUD operations and
 * a custom finder method.
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since Nov 10, 2025
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

	/**
	 * Finds a user by their unique email address.
	 * 
	 * @param email the email of the user to find
	 * @return an Optional containing the user if found, or empty otherwise
	 */
	Optional<User> findByEmail(String email);
}
