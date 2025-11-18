package br.com.eaugusto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.eaugusto.exception.InvalidUserDataException;
import br.com.eaugusto.exception.UserNotFoundException;
import br.com.eaugusto.model.User;
import br.com.eaugusto.repository.IUserRepository;

/**
 * Comprehensive unit tests for {@link UserService} with the goal of achieving
 * maximum branch coverage indirectly — i.e. by exercising the public API of the
 * service so private validators are covered as side effects.
 * <p>
 * STS4 (Spring Tools Suite / Eclipse) has aggressive null-safety analysis and
 * produces a false positive for Mockito matchers (they are compiled as
 * {@code null} placeholders and replaced at runtime). A small number of test
 * methods contain {@code @SuppressWarnings("null")} to avoid those IDE-specific
 * warnings; these suppressions are safe because Mockito supplies the real
 * argument matchers at runtime.
 * </p>
 *
 * The test suite intentionally:
 * <ul>
 * <li>covers valid success paths;</li>
 * <li>covers all failure branches (null/blank inputs) for validators
 * indirectly;</li>
 * <li>exercises constructor guard clauses;</li>
 * <li>verifies password encoding behavior (encoded when provided, skipped when
 * null/blank).</li>
 * </ul>
 *
 * @author Eduardo Augusto (github.com/AsrielDreemurrGM/)
 * @since Nov 16, 2025
 */
class UserServiceTest {

	@Mock
	private IUserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private UserService userService;

	private User validUser;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);

		validUser = User.builder().id(1L).nome("Eduardo").email("eduardo@teste.com").senha("123456").build();
	}

	/**
	 * Saves a valid user and verifies password encoding and repository save call.
	 * Suppression is used to avoid STS4 false-positives on argument matchers.
	 */
	@SuppressWarnings({ "null" })
	@Test
	@DisplayName("Should save user successfully")
	void shouldSaveUserSuccessfully() {
		when(passwordEncoder.encode("123456")).thenReturn("ENCODED");
		when(userRepository.save(any(User.class))).thenReturn(validUser);

		User saved = userService.saveUser(validUser);

		assertNotNull(saved);
		verify(passwordEncoder).encode("123456");
		verify(userRepository).save(any(User.class));
	}

	@Test
	@DisplayName("Should throw InvalidUserDataException when saving null user")
	void shouldThrowWhenSavingNullUser() {
		assertThrows(InvalidUserDataException.class, () -> userService.saveUser(null));
	}

	@Test
	@DisplayName("Should throw InvalidUserDataException when saving user with null name")
	void shouldThrowWhenSavingUserWithNullName() {
		User u = new User(null, null, "a@a.com", "pwd");
		assertThrows(InvalidUserDataException.class, () -> userService.saveUser(u));
	}

	@Test
	@DisplayName("Should throw InvalidUserDataException when saving user with blank name")
	void shouldThrowWhenSavingUserWithBlankName() {
		User u = new User(null, "", "a@a.com", "pwd");
		assertThrows(InvalidUserDataException.class, () -> userService.saveUser(u));
	}

	@Test
	@DisplayName("Should throw InvalidUserDataException when saving user with null email")
	void shouldThrowWhenSavingUserWithNullEmail() {
		User u = new User(null, "Name", null, "pwd");
		assertThrows(InvalidUserDataException.class, () -> userService.saveUser(u));
	}

	@Test
	@DisplayName("Should throw InvalidUserDataException when saving user with blank email")
	void shouldThrowWhenSavingUserWithBlankEmail() {
		User u = new User(null, "Name", "", "pwd");
		assertThrows(InvalidUserDataException.class, () -> userService.saveUser(u));
	}

	@Test
	@DisplayName("Should throw InvalidUserDataException when saving user with null password")
	void shouldThrowWhenSavingUserWithNullPassword() {
		User u = new User(null, "Name", "a@a.com", null);
		assertThrows(InvalidUserDataException.class, () -> userService.saveUser(u));
	}

	@Test
	@DisplayName("Should throw InvalidUserDataException when saving user with blank password")
	void shouldThrowWhenSavingUserWithBlankPassword() {
		User u = new User(null, "Name", "a@a.com", "");
		assertThrows(InvalidUserDataException.class, () -> userService.saveUser(u));
	}

	@Test
	@DisplayName("Should return all users")
	void shouldReturnAllUsers() {
		when(userRepository.findAll()).thenReturn(Arrays.asList(validUser));

		List<User> users = userService.getAllUsers();

		assertEquals(1, users.size());
		verify(userRepository).findAll();
	}

	@Test
	@DisplayName("Should return user by ID when present")
	void shouldReturnUserById() {
		when(userRepository.findById(1L)).thenReturn(Optional.of(validUser));

		Optional<User> result = userService.getUserById(1L);

		assertTrue(result.isPresent());
		assertEquals("Eduardo", result.get().getNome());
	}

	@Test
	@DisplayName("Should return empty optional when user is not found")
	void shouldReturnEmptyOptionalWhenUserNotFound() {
		when(userRepository.findById(1L)).thenReturn(Optional.empty());

		Optional<User> result = userService.getUserById(1L);

		assertTrue(result.isEmpty());
	}

	@Test
	@DisplayName("Should throw InvalidUserDataException when getUserById is called with null ID")
	void shouldThrowWhenGetUserByIdNull() {
		assertThrows(InvalidUserDataException.class, () -> userService.getUserById(null));
	}

	@SuppressWarnings({ "null" })
	@Test
	@DisplayName("Should update user and encode password when provided")
	void shouldUpdateUserAndEncodePasswordWhenProvided() {
		User updates = new User(null, "New Name", "new@a.com", "newPassword");

		when(userRepository.findById(1L)).thenReturn(Optional.of(validUser));
		when(passwordEncoder.encode("newPassword")).thenReturn("ENC_NEW");
		when(userRepository.save(any(User.class))).thenReturn(validUser);

		User updated = userService.updateUser(1L, updates);

		assertNotNull(updated);
		verify(userRepository).findById(1L);
		verify(passwordEncoder).encode("newPassword");
		verify(userRepository).save(any(User.class));
	}

	/**
	 * Verifies that updating a user does not trigger password encoding when the
	 * provided password is {@code null}. The updated user is still saved normally.
	 * Suppression is used to avoid STS4 false-positives on argument matchers.
	 */
	@SuppressWarnings({ "null" })
	@Test
	@DisplayName("Should update user when password is null (not encoded)")
	void shouldUpdateUserWithoutEncodingWhenPasswordIsNull() {
		User updates = new User(null, "New", "new@a.com", null);

		when(userRepository.findById(1L)).thenReturn(Optional.of(validUser));
		when(userRepository.save(any(User.class))).thenReturn(validUser);

		User updated = userService.updateUser(1L, updates);

		assertNotNull(updated);
		verify(passwordEncoder, Mockito.never()).encode(any());
	}

	/**
	 * Verifies that updating a user does not trigger password encoding when the
	 * provided password is blank. This ensures that empty passwords are treated as
	 * "no password change" and are not encoded. Suppression is used to avoid STS4
	 * false-positives on argument matchers.
	 */
	@SuppressWarnings({ "null" })
	@Test
	@DisplayName("Should update user when password is blank")
	void shouldUpdateUserWithoutEncodingWhenPasswordIsBlank() {
		User updates = new User(null, "New", "new@a.com", "");

		when(userRepository.findById(1L)).thenReturn(Optional.of(validUser));
		when(userRepository.save(any(User.class))).thenReturn(validUser);

		User updated = userService.updateUser(1L, updates);

		assertNotNull(updated);
		verify(passwordEncoder, Mockito.never()).encode(any());
	}

	@Test
	@DisplayName("Should throw UserNotFoundException when updating a non-existing user")
	void shouldThrowWhenUpdatingNonExistingUser() {
		when(userRepository.findById(1L)).thenReturn(Optional.empty());
		User updates = new User(null, "Any", "a@a.com", "pwd");

		assertThrows(UserNotFoundException.class, () -> userService.updateUser(1L, updates));
	}

	@Test
	@DisplayName("Should throw InvalidUserDataException when updating with null ID")
	void shouldThrowWhenUpdatingWithNullId() {
		User updates = new User(null, "Name", "a@a.com", "pwd");
		assertThrows(InvalidUserDataException.class, () -> userService.updateUser(null, updates));
	}

	@Test
	@DisplayName("Should throw InvalidUserDataException when updated user is null")
	void shouldThrowWhenUpdatedUserIsNull() {
		assertThrows(InvalidUserDataException.class, () -> userService.updateUser(1L, null));
	}

	@Test
	@DisplayName("Should throw InvalidUserDataException when validating null user (internal validation)")
	void shouldThrowWhenValidatingNullUser() throws Exception {
		var method = UserService.class.getDeclaredMethod("validateUserNameAndEmail", User.class);
		method.setAccessible(true);

		Exception ex = assertThrows(InvocationTargetException.class,
				() -> method.invoke(userService, new Object[] { null }));

		assertTrue(ex.getCause() instanceof InvalidUserDataException);
	}

	@ParameterizedTest
	@MethodSource("invalidUpdateUserProvider")
	@DisplayName("Should throw InvalidUserDataException when updated user has invalid name or email")
	void shouldThrowWhenUpdatedUserHasInvalidFields(User updates) {
		when(userRepository.findById(1L)).thenReturn(Optional.of(validUser));

		assertThrows(InvalidUserDataException.class, () -> userService.updateUser(1L, updates));
	}

	private static Stream<User> invalidUpdateUserProvider() {
		return Stream.of(new User(null, null, "a@a.com", "pwd"), new User(null, "", "a@a.com", "pwd"),
				new User(null, "Name", null, "pwd"), new User(null, "Name", "", "pwd"));
	}

	@Test
	@DisplayName("Should delete user successfully")
	void shouldDeleteUserSuccessfully() {
		when(userRepository.existsById(1L)).thenReturn(true);

		userService.deleteUser(1L);

		verify(userRepository).deleteById(1L);
	}

	@Test
	@DisplayName("Should throw UserNotFoundException when deleting non-existing user")
	void shouldThrowWhenDeletingNonExistingUser() {
		when(userRepository.existsById(1L)).thenReturn(false);

		assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));
	}

	@Test
	@DisplayName("Should throw InvalidUserDataException when deleteUser is called with null ID")
	void shouldThrowWhenDeleteWithNullId() {
		assertThrows(InvalidUserDataException.class, () -> userService.deleteUser(null));
	}

	@Test
	@DisplayName("Should throw IllegalArgumentException when repository is null in constructor")
	void shouldThrowWhenRepositoryIsNullInConstructor() {
		assertThrows(IllegalArgumentException.class, () -> new UserService(null, passwordEncoder));
	}

	@Test
	@DisplayName("Should throw IllegalArgumentException when passwordEncoder is null in constructor")
	void shouldThrowWhenPasswordEncoderIsNullInConstructor() {
		assertThrows(IllegalArgumentException.class, () -> new UserService(userRepository, null));
	}
}
