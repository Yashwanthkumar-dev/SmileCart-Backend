package SmileCart.App.DTO;

import SmileCart.App.Model.Role;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UserRegistration {
	private String email;
	private String password;
	private Role role;

	public String getEmail() {
		return email;
	}

	public void setEmail(String userEmail) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String userPassword) {
		this.password = userPassword;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
