package SmileCart.App.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SmileCart.App.Model.UserEntity;
import SmileCart.App.Repository.UserRepository;
import SmileCart.App.Service.UserEntityService;
import SmileCart.App.security.JwtUtil;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserEntityService userService;
	@Autowired
	private PasswordEncoder passwordencoder;
	@Autowired
	private JwtUtil util;

//	user registration
	@PostMapping("/registration")
	public ResponseEntity<String> userRegistration(@RequestBody Map<String, String> body) {
		String email = body.get("email");
		String password = body.get("password");

		if (userRepo.findByemail(email).isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User was already exists");
		}
		userService.userRegistration(email, password);
		return ResponseEntity.status(HttpStatus.CREATED).body("successfully created");
	}

// user login
	@PostMapping("/login")
	public ResponseEntity<?> userLogin(@RequestBody Map<String, String> body) {
		String email = body.get("email");
		String password = body.get("password");
		var userOptional = userRepo.findByemail(email);
		if (userOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user was not found");
		}
		UserEntity user = userOptional.get();
		if (!passwordencoder.matches(password, user.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid user");
		}
		String token = util.generatToken(email);
		return ResponseEntity.ok(Map.of("token", token));
	}

}
