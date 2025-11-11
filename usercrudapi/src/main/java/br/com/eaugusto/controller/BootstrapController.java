package br.com.eaugusto.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eaugusto.model.User;
import br.com.eaugusto.repository.IUserRepository;
import br.com.eaugusto.security.JWTUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 *
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since Nov 11, 2025
 */
@RestController
public class BootstrapController {

	private static final String ADMINEMAIL = "admin@gmail.com";

	private final IUserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JWTUtil jwtUtil;

	@PersistenceContext
	private EntityManager entityManager;

	public BootstrapController(IUserRepository userRepository, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

	@CrossOrigin(origins = "http://localhost:5173")
	@GetMapping(value = "/api/bootstrap", produces = "text/plain")
	@Transactional
	public ResponseEntity<String> bootstrap() {
		Optional<Boolean> initializedOptional = entityManager
				.createQuery("SELECT a.initialized FROM AppInit a WHERE a.id = 1", Boolean.class).getResultStream()
				.findFirst();

		if (initializedOptional.orElse(false)) {
			return ResponseEntity.status(403).body("Bootstrap already executed.");
		}

		if (!userRepository.findByEmail(ADMINEMAIL).isPresent()) {
			User admin = new User();
			admin.setNome("Admin");
			admin.setEmail(ADMINEMAIL);
			admin.setSenha(passwordEncoder.encode("admin12345"));
			userRepository.save(admin);
		}

		entityManager.createNativeQuery("UPDATE app_init SET initialized = TRUE WHERE id = 1").executeUpdate();

		String token = jwtUtil.generateToken(userRepository.findByEmail(ADMINEMAIL).get());
		return ResponseEntity.ok(token);
	}
}
