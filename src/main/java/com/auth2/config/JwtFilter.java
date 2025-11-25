package com.auth2.config;

import com.auth2.service.CustomerUserDetailsService;
import com.auth2.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final CustomerUserDetailsService customerUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Read token from request header
        String token = request.getHeader("Authorization");
        // Check if token exists and starts with "Bearer "
        if(token!=null && token.startsWith("Bearer ")){
            // Remove the "Bearer " prefix → get only actual token
            String rawToken = token.substring(7);
            // Validate token and extract username
            String username = jwtService.validateTokenAndRetrieveSubject(rawToken);
            // Continue only if:
            // 1. Token returned a username
            // 2. User is not already authenticated
            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                // Load complete user profile (roles + authorities)
                UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
                // Create authentication object for Spring Security
                var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                // Attach request details (IP address / device / etc.)
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Mark user as authenticated for this request
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
            // Continue with the remaining filters / controller
        }filterChain.doFilter(request,response);

    }
}
