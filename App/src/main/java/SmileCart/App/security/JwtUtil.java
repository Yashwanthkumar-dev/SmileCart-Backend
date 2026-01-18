package SmileCart.App.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import SmileCart.App.Model.UserEntity;
import SmileCart.App.Repository.UserRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
//	 secret key
	private final String secretKey = "SmileCartLearningJWTSecretKey12345678901234567890";
	private SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

//	Expiration 
	private final int EXPIRATION = 1000 * 60 * 60;
	@Autowired
	private UserRepository userRepo;

//	Generate token
	public String generateToken(String userEmail) {
		Optional<UserEntity> user = userRepo.findByemail(userEmail);
		return Jwts.builder().subject(userEmail).claim("role", user.get().getRole().name()).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + EXPIRATION)).signWith(key).compact();
	}

//	extract userEmail
	public String extractUserEmail(String token) {
		return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
	}

//	extract roles
	public String extractRoles(String token) {
		String rolesString = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().get("role",
				String.class);
		return rolesString;
	}

//	validate token
	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}
}
