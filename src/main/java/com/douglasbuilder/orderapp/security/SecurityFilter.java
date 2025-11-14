package com.douglasbuilder.orderapp.security;

import com.douglasbuilder.orderapp.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

  private final TokenService tokenService;
  private final UserService userService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String token = recoverToken(request);
    if (token != null && tokenService.isTokenValid(token)) {
      String email = tokenService.extractTokenSubject(token);
      UserDetails user = userService.findByEmail(email);
      var authentication =
          new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }

  private String recoverToken(HttpServletRequest httpServletRequest) {
    var authHeader = httpServletRequest.getHeader("Authorization");
    if (authHeader == null) return null;
    return authHeader.replace("Bearer ", "");
  }
}
