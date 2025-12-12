package com.douglasbuilder.orderapp.model;

import com.douglasbuilder.orderapp.model.enumetations.UserRoles;
import com.douglasbuilder.orderapp.valueObject.Address;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column private String firstName;

  @Column private String lastName;

  @Column(unique = true)
  private String email;

  @Column private String password;

  @Column private UserRoles role;

  @Column private LocalDateTime createdAt;

  @Column private LocalDateTime lastLogin;

  @Column private String phoneCountry;

  @Column private String phoneNumber;

  @Column @Embedded Address address;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
  }

  public String getFullName() {
    return getFirstName() + " " + getLastName();
  }

  public String getFullPhoneNumber() {
    return "+" + getPhoneCountry() + " " + getPhoneNumber();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (role == null) {
      return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
    return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return UserDetails.super.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return UserDetails.super.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return UserDetails.super.isEnabled();
  }

}
