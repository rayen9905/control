package com.pfe.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "visiteur")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class Visiteur {

  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  private Integer id;
  @Column(name = "firstName", length = 50)
  private String firstname;
  @Column(name = "lastname", length = 50)

  private String lastname;
  @Column(name = "email", length = 50)
  private String email;
  @Column(name = "CodePin", length = 50)
  private String CodePin;
  @Column(name = "CodeUid", length = 50)
  private String CodeUid;
  @Column(name = "Phone", length = 50)
  private String Phone;
  @Column(name = "Address", length = 200)
  private String Adresse;
  @Column(name = "Image", length = 300)
  private String Image;
  @ManyToOne
  @JsonIgnoreProperties("usr")
  private Profile prof;
 @ManyToMany
 @JsonIgnoreProperties("usr")
 private List<Porte> prt;

  @Enumerated(EnumType.STRING)
  private Role role;
/*
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
  }*/
}
