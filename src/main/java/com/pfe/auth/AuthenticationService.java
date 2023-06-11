package com.pfe.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfe.Controller.UserService;
import com.pfe.config.JwtService;
import com.pfe.entities.*;
import com.pfe.repos.RefreshTokenRepository;
import com.pfe.repos.TokenRepository;
import com.pfe.repos.UserRepository;
import com.pfe.repos.VisiteurRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  @Autowired
  private final UserService repository1;

  private final TokenRepository tokenRepository;
  private final RefreshTokenRepository refreshtokenRepository;
  private final UserRepository UserRepository;
  private final VisiteurRepository VisRepository;

  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(User request) {
    User in=new User();
    request.setPassword(passwordEncoder().encode(request.getPassword()));
    in=request;
   /* in.setFirstname(request.getFirstname());
    in.setLastname(request.getLastname());
    in.setPhone(request.getPhone());
    in.setAdresse(request.getAdresse());
    in.setImage(request.getImage());
    in.setRole(request.getRole());
    in.setEmail(request.getEmail());
    in.setPassword(request.getPassword());
    in.setRole(Role.USER);*/
    /*var user = inv.builder().build()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.USER)
        .build();*/
    var savedUser = repository.save(in);
    var jwtToken = jwtService.generateToken(in);
    var refreshToken = jwtService.generateRefreshToken(in);

    saveUserToken(savedUser, jwtToken);
    saveUserrefreshToken(savedUser, refreshToken);
    return AuthenticationResponse.builder()
        .token(jwtToken)
            .refreshToken(refreshToken)
        .build();
  }
  public AuthenticationResponse registerAdmin(User request) {
   /* var user = User.builder()
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.ADMIN)
            .build();*/
    User in=new User();
    request.setPassword(passwordEncoder().encode(request.getPassword()));
    request.setRole(Role.ADMIN);
    in=request;
    /*in.setFirstname(request.getFirstname());
    in.setLastname(request.getLastname());
    in.setEmail(request.getEmail());
    in.setPassword(request.getPassword());
    in.setRole(Role.USER);*/
    var savedUser = repository.save(in);
    var jwtToken = jwtService.generateToken(in);
    var refreshToken = jwtService.generateRefreshToken(in);

    saveUserToken(savedUser, jwtToken);
    return AuthenticationResponse.builder()
            .token(jwtToken)
            .refreshToken(refreshToken)
            .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = repository1.findByEmail(request.getEmail())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }
  public AuthenticationResponse ReloadRefresh(String email) {
    /*authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            )
    );*/
    var user = repository1.findByEmail(email)
            .orElseThrow();
    var jwtRefreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserRefreshTokens(user);
    saveUserrefreshToken(user, jwtRefreshToken);
    return AuthenticationResponse.builder()
            .refreshToken(jwtRefreshToken)
            .build();
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }
  private void saveUserrefreshToken(User user, String jwtToken) {
    var token = Refreshtoken.builder()
            .user(user)
            .token(jwtToken)
            .tokenType(RefreshType.BEARER)
            .expired(false)
            .revoked(false)
            .build();
    refreshtokenRepository.save(token);
    user.setRef(token);
    VisRepository.save(user);
  }

  private void revokeAllUserRefreshTokens(User user) {
    var validUserTokens = refreshtokenRepository.findAllValidTokenByUser(user.getId());
    /*if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });*/
    user.setRef(null);
    VisRepository.save(user);
    refreshtokenRepository.deleteAll(validUserTokens);
  }
  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }
  public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository1.findByEmail(userEmail)
              .orElseThrow();
      var isTokenValid = refreshtokenRepository.findByToken(refreshToken)
              .map(t -> !t.isExpired() && !t.isRevoked())
              .orElse(false);
      if (jwtService.isTokenValid(refreshToken, user)&& isTokenValid) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .token(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
  /*protected void doFilterInternal(
          @NonNull HttpServletRequest request,
          @NonNull HttpServletResponse response,
          @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    jwt = authHeader.substring(7);
    userEmail = jwtService.extractUsername(jwt);
    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
      var isTokenValid = tokenRepository.findByToken(jwt)
              .map(t -> !t.isExpired() && !t.isRevoked())
              .orElse(false);
      if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }
    filterChain.doFilter(request, response);
  }
}*/
  @Bean
  private PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
