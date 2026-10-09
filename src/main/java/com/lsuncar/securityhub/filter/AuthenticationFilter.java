package com.lsuncar.securityhub.filter;

import com.lsuncar.securityhub.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class AuthenticationFilter extends OncePerRequestFilter
{

	private final JwtUtil jwtUtils;
	private final UserDetailsService userDetailsService;

	public AuthenticationFilter( JwtUtil jwtUtils, UserDetailsService userDetailsService) {
		this.jwtUtils = jwtUtils;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {

		try {
			final String authHeader = request.getHeader( HttpHeaders.AUTHORIZATION);
			final String jwt;
			final String username;
			if (isNull(authHeader) || !authHeader.startsWith("Bearer")) {
				filterChain.doFilter(request, response);
				return;
			}
			jwt = authHeader.substring(7); //Bearer_ 7 chars
			username = jwtUtils.extractUsername(jwt);
			if (nonNull(username) && isNull( SecurityContextHolder.getContext().getAuthentication())) {
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
				if (jwtUtils.isTokenValid(jwt, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
							userDetails,
							null,
							userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
			filterChain.doFilter(request, response);
		}
		catch ( ExpiredJwtException ejwtException ) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json");
			final Map<String, Object> body = new HashMap<>();
			body.put( "path", request.getServletPath() );
			body.put( "statusCode", HttpServletResponse.SC_UNAUTHORIZED );
			body.put( "error", "unauthorized" );
			//body.put( "message", ResourceBundleUtil.getLocaleText("pleaseLoginAgain") );

			final ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue( response.getOutputStream(), body );
		}
		catch ( Exception e ) {
			logger.error( "Error", e );
			throw e;
		}




	}
}
