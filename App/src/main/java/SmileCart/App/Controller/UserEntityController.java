	package SmileCart.App.Controller;
	
	import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import SmileCart.App.Service.UserEntityService;
	@CrossOrigin(origins = "*")
	@RestController
	@RequestMapping("/api/v1/user")
	public class UserEntityController {
		@Autowired
		private UserEntityService userService;
	
		// view all user
		@PreAuthorize("hasAuthority('admin')")
		@GetMapping
		public ResponseEntity<?> getAllUser() {
			return userService.getAllUser();
		}
	
		// View All user by email
		@GetMapping("/email")
		public ResponseEntity<?> getUserByEmail(@RequestParam String userEmail) {
			return userService.getUserByEmail(userEmail);
		}
	
		// create new User
		@PostMapping
		public ResponseEntity<?> createUser(@RequestParam String firstName, @RequestParam String lastName,
				@RequestParam String email, @RequestParam String password, @RequestParam Long phoneNumber) {
			return userService.createUser(firstName, lastName, email, password, phoneNumber);
		}
	
		// update user
		@PutMapping
		public ResponseEntity<?> updateUser(@RequestParam String firstName, @RequestParam String lastName,
				@RequestParam String newEmail, @RequestParam String oldEmail, @RequestParam String password,
				@RequestParam Long phoneNumber) {
			return userService.updateUser(firstName, lastName, newEmail, oldEmail, password, phoneNumber);
		}
	
		// delete all User
		@DeleteMapping
		@PreAuthorize("hasAuthority('admin')")
		public ResponseEntity<?> deleteAllUser() {
			return userService.deleteAllUser();
		}
	
		// delete user by id
		@PreAuthorize("hasAuthority('admin')")
		@DeleteMapping("/{id}")
		public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
			return userService.deleteUserById(id);
		}
		
		@PreAuthorize("hasAuthority('admin')")
		@DeleteMapping("/email/{email}")
		public ResponseEntity<?>deleteUserByEmail(@PathVariable String email){
			return userService.deleteUserByEmail(email);
		}
		
	}
