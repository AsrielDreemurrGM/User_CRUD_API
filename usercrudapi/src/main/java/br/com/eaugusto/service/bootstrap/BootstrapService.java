package br.com.eaugusto.service.bootstrap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Service component responsible for providing bootstrap-related operations.
 * Currently exposes the database password as the default admin password.
 * 
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since Nov 11, 2025
 */
@Component
public class BootstrapService {

	@Value("${spring.datasource.password}")
	private String dbPassword;

	public String getAdminPassword() {
		return dbPassword;
	}
}
