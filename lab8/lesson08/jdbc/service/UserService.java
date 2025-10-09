package lesson08.jdbc.service;

import java.sql.SQLException;
import java.util.List;

import lesson08.jdbc.model.User;
import lesson08.jdbc.repository.UserRepository;

public class UserService {
	private final UserRepository userRepository;

	public UserService() {
		this.userRepository = new UserRepository();
	}

	// Create a new user
	public void createUser(String name, String email) throws SQLException {
		User user = new User(0, name, email); // ID is auto-generated
		userRepository.createUser(user);
	}

	// Read all users
	public List<User> readUsers() throws SQLException {
		return userRepository.readUsers();
	}

	// Update a user
	public void updateUser(int id, String name, String email) throws SQLException {
		User user = new User(id, name, email);
		userRepository.updateUser(user);
	}

	// Delete a user
	public void deleteUser(int id) throws SQLException {
		userRepository.deleteUser(id);
	}
}
