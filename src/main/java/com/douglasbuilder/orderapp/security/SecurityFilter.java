package com.douglasbuilder.orderapp.security;

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
      log.debug("Processing JWT token for request: " + request.getRequestURI()); // ✅ Use log

      String email = tokenService.validateToken(token);
      if (!email.isEmpty()) {
        UserDetails user = userRepository.findByEmail(email);
        if (user != null) {
          log.debug("Authenticated user: " + email); // ✅ Use log
          var authentication =
              new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
          log.warn("User not found for email: " + email); // ✅ Use log
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
