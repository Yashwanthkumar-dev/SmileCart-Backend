package SmileCart.App.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
@Component
public class JwtUtil {
private static final String SECRET_KEY_STRING = "jCveYTNca4TWRjBLSP8eCyecqIv7OXwo";
private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());
private final long EXPIRATION = 1000*60*60;


// Generate token 
	public String generatToken( String email) {
		return Jwts.builder()
				.subject(email)
				.issuedAt(new Date(System.currentTimeMillis()) )
				.expiration( new Date(System.currentTimeMillis()+EXPIRATION))
				.signWith(SECRET_KEY,Jwts.SIG.HS256)
				.compact();
	}
	
//	Validate token
	public boolean validateToken(String token) {
		try {
			Jwts.parser()
			.verifyWith(SECRET_KEY).build().parseSignedClaims(token);
			return true;
		} catch (JwtException e) {
			return false;
		}
	}
	
	
}
