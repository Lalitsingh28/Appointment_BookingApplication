package com.example.demo.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.config.CustomUser;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthToken extends OncePerRequestFilter {
	
	  @Autowired
	  private JwtUtils jwtUtils;

	  @Autowired
	  private CustomUser customUser;


	  @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

	        // Get token from request
	        String token = getTokenFromRequest(request);

	        // Validate token using JWT provider
	        if(token != null && jwtUtils.validateToken(token)) {

	            // Get username from token
	            String username = jwtUtils.getUsernameFromToken(token);

	            // Get user details
	            UserDetails userDetails = customUser.loadUserByUsername(username);

	            // Create authentication object
	            var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

	            // Set user to spring context
	            SecurityContextHolder.getContext()
	                    .setAuthentication(auth);

	        }

	        filterChain.doFilter(request, response);
	    }

	    private String getTokenFromRequest(HttpServletRequest request) {
	        // Extract authentication header
	        var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

	        // Bearer {JWT}

	        // Check whether it starts with `Bearer ` or not
	        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")){
	            return authHeader.substring(7);
	        }

	        return null;
	    }

}
