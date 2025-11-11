package br.com.eaugusto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eaugusto.exception.AdminNotFoundException;
import br.com.eaugusto.model.User;
import br.com.eaugusto.repository.IUserRepository;
import br.com.eaugusto.security.JWTUtil;
import br.com.eaugusto.service.bootstrap.BootstrapService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * Controller responsible for bootstrapping the application. Creates an initial
 * admin user if none exists and marks the application as initialized.
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since Nov 11, 2025
 */
@RestController
public class BootstrapController {

	private static final String ADMINEMAIL = "admin@gmail.com";

	private final BootstrapService bootstrapService;
	private final IUserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JWTUtil jwtUtil;

	@PersistenceContext
	private EntityManager entityManager;

	public BootstrapController(IUserRepository userRepository, PasswordEncoder passwordEncoder, JWTUtil jwtUtil,
			BootstrapService bootstrapService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
		this.bootstrapService = bootstrapService;
	}

	@CrossOrigin(origins = "http://localhost:5173")
	@GetMapping(value = "/api/bootstrap", produces = "text/plain")
	@Transactional
	public ResponseEntity<String> bootstrap() {
		if (userRepository.findByEmail(ADMINEMAIL).isEmpty()) {
			User admin = new User();
			admin.setNome("Admin");
			admin.setEmail(ADMINEMAIL);
			admin.setSenha(passwordEncoder.encode(bootstrapService.getAdminPassword()));
			userRepository.save(admin);
		}

		entityManager.createNativeQuery("UPDATE app_init SET initialized = TRUE WHERE id = 1").executeUpdate();

		User admin = userRepository.findByEmail(ADMINEMAIL)
				.orElseThrow(() -> new AdminNotFoundException("Admin user not found after bootstrap"));

		String token = jwtUtil.generateToken(admin);
		return ResponseEntity.ok(token);
	}
}
