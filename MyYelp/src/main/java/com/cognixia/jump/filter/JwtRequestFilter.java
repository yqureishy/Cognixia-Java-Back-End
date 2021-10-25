package com.cognixia.jump.filter;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.cognixia.jump.service.MyUserDetailsService;
import com.cognixia.jump.util.JwtUtil;
@Component
public class JwtRequestFilter extends OncePerRequestFilter{
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
	
		final String authorisationHeader = request.getHeader("Authorisation");
		
		String username = null;
		
		String jwt = null;
		
		
		
		if (authorisationHeader != null && authorisationHeader.startsWith("Bearer ")) {
			
			jwt = authorisationHeader.substring(7);
			username = jwtUtil.extractUsername(jwt);
			
		}
		
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			if (jwtUtil.validateToken(jwt, userDetails)) {
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
					.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}			
		}
		chain.doFilter(request, response);
		
		
		
		
	}
	
}