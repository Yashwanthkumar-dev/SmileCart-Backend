package SmileCart.App.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SmileCart.App.DTO.UserRegistration;
import SmileCart.App.Model.Role;
import SmileCart.App.Model.UserEntity;
import SmileCart.App.Repository.UserRepository;
import SmileCart.App.security.JwtUtil;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/authentication")
public class AuthentionController {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private AuthenticationManager authManager;
		@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtUtil util;

//	User Registration 
	@PostMapping("/registration")
	public ResponseEntity<?> userRegistration(@RequestBody UserRegistration dto) {
//		check user exits 
		if (userRepo.findByemail(dto.getEmail()).isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("user was already exits");
		}
//		create new user
		UserEntity newUser = new UserEntity();
		newUser.setEmail(dto.getEmail());
		newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
		newUser.setFirstName(dto.getFirstName());
		newUser.setLastName(dto.getLastName());
		newUser.setPhoneNumber(dto.getPhoneNumber());
		System.out.println("Received email "+ dto.getEmail());
//		role
		if (dto.getRole() != null) {
			newUser.setRole(dto.getRole());
		} else {
			newUser.setRole("admin");
		}
		userRepo.save(newUser);
		return ResponseEntity.status(HttpStatus.CREATED).body("User was successfully created");

	}

//	User Login
	@PostMapping("/login")
	public ResponseEntity<?> userLogin(@RequestBody UserEntity loginRequest) {
		try {
			authManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid Email and Password");
		}
		UserEntity user = userRepo.findByemail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

		String token = util.generateToken(loginRequest.getEmail());
		return ResponseEntity.ok(Map.of(
				"token",token,
				"role",user.getRole(),
				"email",user.getEmail()
				));
	}

}
