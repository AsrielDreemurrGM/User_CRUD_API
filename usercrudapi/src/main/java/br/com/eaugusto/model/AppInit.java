package br.com.eaugusto.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since Nov 11, 2025
 */
@Entity
@Table(name = "app_init")
@Data
public class AppInit {

	@Id
	private Long id;
	private Boolean initialized;
}
