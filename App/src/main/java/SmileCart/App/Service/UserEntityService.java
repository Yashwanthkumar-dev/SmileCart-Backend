package SmileCart.App.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import SmileCart.App.DTO.UserRecord;
import SmileCart.App.Model.UserEntity;
import SmileCart.App.Repository.UserRepository;

@Service
public class UserEntityService {
	@Autowired
	private UserRepository userRepo;

	public ResponseEntity<?> getAllUser() {
		try {
			List<UserEntity> allUser = userRepo.findAll();
			List<UserRecord> allUserRecords = allUser.stream().map((s) -> {
				UserRecord user = new UserRecord(s.getFirstName(), s.getLastName(), s.getEmail(), s.getPhoneNumber());
				return user;
			}).toList();

			if (allUserRecords.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Users are not found");
			}
			return ResponseEntity.status(HttpStatus.OK).body(allUserRecords);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	public ResponseEntity<?> getUserByEmail(String userEmail) {
		try {
			Optional<UserEntity> isUser = userRepo.findByemail(userEmail);
			if (isUser.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User was not found");
			}
			UserEntity user = isUser.get();
			UserRecord newUserRecord = new UserRecord(user.getFirstName(), user.getLastName(), user.getEmail(),
					user.getPhoneNumber());
			return ResponseEntity.status(HttpStatus.OK).body(newUserRecord);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	public ResponseEntity<?> createUser(String firstName, String lastName, String email, String password,
			Long phoneNumber) {
		try {
			Optional<UserEntity> isUser = userRepo.findByemail(email);
			if (isUser.isPresent()) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("user was already existed");
			}
			UserEntity newUser = new UserEntity();
			newUser.setFirstName(firstName);
			newUser.setLastName(lastName);
			newUser.setEmail(email);
			newUser.setPassword(password);
			newUser.setPhoneNumber(phoneNumber);
			userRepo.save(newUser);
			return ResponseEntity.status(HttpStatus.CREATED).body("created successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	public ResponseEntity<?> updateUser(String firstName, String lastName, String newEmail, String oldEmail,
			String password, Long phoneNumber) {
		try {
			Optional<UserEntity> isUser = userRepo.findByemail(oldEmail);
			if (isUser.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user was not found");
			}
			UserEntity updateUser = isUser.get();
			updateUser.setFirstName(firstName);
			updateUser.setLastName(lastName);
			updateUser.setEmail(newEmail);
			updateUser.setPassword(password);
			updateUser.setPhoneNumber(phoneNumber);
			userRepo.save(updateUser);
			return ResponseEntity.status(HttpStatus.CREATED).body("updated successfully");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	public ResponseEntity<?> deleteAllUser() {
		try {
			userRepo.deleteAll();
			return ResponseEntity.status(HttpStatus.OK).body("All Users are deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	public ResponseEntity<?> deleteUserByEmail(Long id) {
		try {
			Optional<UserEntity> user = userRepo.findById(id);
			if (user.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user was not found");
			}
			userRepo.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}
