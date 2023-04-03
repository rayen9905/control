package com.pfe.entities;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User implements UserDetails {

  @Id
  @GeneratedValue
  private Integer id;
  @Column(name = "firstName", length = 50)
  private String firstname;
  @Column(name = "lastname", length = 50)

  private String lastname;
  @Column(name = "email", length = 50)

  private String email;
  @Column(name = "password")

  private String password;
  @Column(name = "Code", length = 50)
  private String Code;
  @Column(name = "Phone", length = 50)
  private String Phone;
  @Column(name = "Address", length = 200)
  private String Adresse;
  @Column(name = "Image", length = 300)
  private String Image;
  @ManyToOne
  private Profile prof;
  @ManyToOne
  private Departement depar;
  @OneToMany(mappedBy = "user")
  private List<Token> tokens;
  @Enumerated(EnumType.STRING)
  private Role role;


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
