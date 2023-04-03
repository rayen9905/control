package com.pfe.DTO;

import com.pfe.entities.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String firstname;

    private String lastname;

    private String email;

    private String password;
    private String Code;
    private String Phone;
    private String Adresse;
    private String Image;
    private Long prof;
    private Long depar;
    private List<Token> tokens;
    private Role role;
    private Collection<GrantedAuthority> auth;

    public static UserDto toDto(User u){
        return UserDto.builder()
                .id(u.getId())
                .firstname(u.getFirstname())
                .lastname(u.getLastname())
                .email(u.getEmail())
                .password(u.getPassword())
                .Code(u.getCode())
                .Phone(u.getPhone())
                .Adresse(u.getAdresse())
                .depar(u.getDepar().getIdDep())
                .Image(u.getImage())
                .prof(u.getProf().getIdProf())
                .depar(u.getDepar().getIdDep())
                .prof(u.getProf().getIdProf())
                .auth((Collection<GrantedAuthority>) u.getAuthorities())
                .build();}


    }


