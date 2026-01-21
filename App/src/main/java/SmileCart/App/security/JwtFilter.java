package SmileCart.App.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import SmileCart.App.SecurityService.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtil util;
	@Autowired
	private CustomUserDetails customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
//		header taking from token
		String authHeader = request.getHeader("Authorization");

//		check it whether this is null or not start with Bearer with space
//		false token naa if statement kulla irukara code exceut agum
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
//		token correct ahh irunthuchu naa antha header la irukara bearer ahh remove pannanum

		String token = authHeader.substring(7);

//		token vechu try catch open pannitu athula extract userName or userEmail
		try {
			String userEmail = util.extractUserEmail(token);

//			user name or user email null irukka kudathu aprm authentication pannatha user ahh irukanum athukku securityContextHolder().getContext().getAuthentication()==null ah irukkanum
			if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);

//				user kku ipo token validate panni send pannanum

				if (util.validateToken(token)) {
					UsernamePasswordAuthenticationToken authenticatedToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authenticatedToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticatedToken);

				}
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		filterChain.doFilter(request, response);

	}

}
