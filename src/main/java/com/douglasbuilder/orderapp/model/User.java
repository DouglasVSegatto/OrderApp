package com.douglasbuilder.orderapp.model;

import com.douglasbuilder.orderapp.model.enumetations.UserRoles;
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

  @Column private String addressStreet;

  @Column private String addressNumber;

  @Column private String addressCity;

  @Column private String addressState;

  @Column private String addressCountry;

  @Column private String addressZipcode;

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

  public String getFullAddress() {
    StringBuilder address = new StringBuilder();

    if (addressStreet != null && !addressStreet.trim().isEmpty()) {
      address.append(addressStreet);
      if (addressNumber != null && !addressNumber.trim().isEmpty()) {
        address.append(", ").append(addressNumber);
      }
    }

    if (addressCity != null && !addressCity.trim().isEmpty()) {
      if (!address.isEmpty()) address.append(", ");
      address.append(addressCity);
    }

    if (addressState != null && !addressState.trim().isEmpty()) {
      if (!address.isEmpty()) address.append(", ");
      address.append(addressState);
    }

    if (addressZipcode != null && !addressZipcode.trim().isEmpty()) {
      if (!address.isEmpty()) address.append(" ");
      address.append(addressZipcode);
    }

    if (addressCountry != null && !addressCountry.trim().isEmpty()) {
      if (!address.isEmpty()) address.append(", ");
      address.append(addressCountry);
    }

    return address.toString();
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
