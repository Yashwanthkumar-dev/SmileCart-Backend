package SmileCart.App.SecurityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import SmileCart.App.Model.UserEntity;
import SmileCart.App.Repository.UserRepository;

public class CustomUserDetails implements UserDetailsService {
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepo.findByemail(username)
				.orElseThrow(() -> new UsernameNotFoundException("user was not found"));
		return new User(user.getEmail(), user.getPassword(), null);
	}

}
