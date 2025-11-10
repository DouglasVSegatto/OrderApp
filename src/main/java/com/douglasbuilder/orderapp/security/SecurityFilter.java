package com.douglasbuilder.orderapp.security;

import com.douglasbuilder.orderapp.model.User;
import com.douglasbuilder.orderapp.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityFilter extends OncePerRequestFilter {

  private final TokenService tokenService;
  private final UserRepository userRepository;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = recoverToken(request);

    if (token != null) {
      logger.debug("Processing JWT token for request: " + request.getRequestURI());

      String email = tokenService.validateToken(token);
      if (!email.isEmpty()) {
        UserDetails user = userRepository.findByEmail(email);
        if (user != null) {
          logger.debug("Authenticated user: " + email);

          var authentication =
              new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(authentication);

          if (tokenService.isTokenNearExpiry(token)) {
            String newToken = tokenService.generateToken((User) user);
            response.setHeader("X-New-Token", "Bearer " + newToken);
          }
        } else {
          logger.warn("User not found for email: " + email);
        }
      }
    }
    filterChain.doFilter(request, response);
  }

  private String recoverToken(HttpServletRequest httpServletRequest) {
    var authHeader = httpServletRequest.getHeader("Authorization");
    if (authHeader == null) return null;
    return authHeader.replace("Bearer ", "");
  }
}
