package com.example.myspace.security;

import com.example.myspace.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import io.jsonwebtoken.Jwts;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    // The token is processed
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // The header authorization is obtained
        String header = request.getHeader(Constants.HEADER_AUTHORIZACION_KEY);
        // If the header does not exist or does not start with "Bearer", the authentication is not followed
        if (header == null || !header.startsWith(Constants.TOKEN_BEARER_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        // If the token exist, the authentication object is obtained and this is saved into context
        UsernamePasswordAuthenticationToken authentication = getAuthenticationToken(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);

    }

    // The authentication object is obtain from the token
    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {

        // The token is obtained from Authorization header
        String token = request.getHeader(Constants.HEADER_AUTHORIZACION_KEY);
        if (token != null) {
            // The token is processed and the userEmail is recovered (userEmail = username)
            String userEmail = Jwts.parser()
                    .setSigningKey(Constants.SIGNING_KEY)
                    .parseClaimsJws(token.replace(Constants.TOKEN_BEARER_PREFIX, ""))
                    .getBody()
                    .getSubject();

            // If userEmail is in the token and there is not an authentication object in the context
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // The user is loaded by username, in this case username is equal to userEmail
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

                // If the userEmail is equal that the load userEmail, the authentication object is created
                if (userDetails.getUsername().equals(userEmail)) {
                    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                }
            }
        }
        return null;
    }
}

