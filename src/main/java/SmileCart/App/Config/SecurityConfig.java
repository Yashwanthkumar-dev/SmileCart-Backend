package SmileCart.App.Config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import SmileCart.App.security.JwtFilter;

@EnableMethodSecurity
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private JwtFilter Filter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.configurationSource(request -> {
					CorsConfiguration config = new CorsConfiguration();
					// Indha oru line-ah mattum maathunga
					config.setAllowedOriginPatterns(Collections.singletonList("*"));
					config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
					config.setAllowedHeaders(Arrays.asList("*"));
					config.setAllowCredentials(true);
					return config;
	        }))
	        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
	            .requestMatchers("/authentication/**").permitAll()
	            .requestMatchers("/api/admin/**").hasAnyAuthority("admin", "ROLE_admin")
	            .requestMatchers(HttpMethod.DELETE, "/api/v1/user/**").hasAnyAuthority("admin", "ROLE_admin")
	            .requestMatchers("/api/v1/product/**", "/api/v1/category/**").permitAll()
	            .requestMatchers("/api/v1/cart/**", "/api/v1/order/**").authenticated()
	            .anyRequest().authenticated()
	        )
	        .addFilterBefore(Filter, UsernamePasswordAuthenticationFilter.class);
	    return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
