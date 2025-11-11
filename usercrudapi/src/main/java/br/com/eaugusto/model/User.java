package br.com.eaugusto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a user in the system. Maps to the "usuarios" table
 * in the PostgreSQL database.
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since Nov 10, 2025
 */
@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	/** Unique identifier for the user. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** Full name of the user. */
	@Column(nullable = false, length = 255)
	private String nome;

	/** Unique email address of the user. */
	@Column(nullable = false, unique = true, length = 100)
	private String email;

	/** Encrypted password of the user. */
	@Column(nullable = false, length = 255)
	private String senha;
}
