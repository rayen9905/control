package com.pfe.DTO;

import com.pfe.entities.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
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
    private String CodePin;
    private String CodeUid;
    private String Phone;
    private String Adresse;
    private String Image;
    private ProfileDto prof;
    private List<Token> tokens;
    private List<PorteDto> prt;
    private Role role;
    private Collection<GrantedAuthority> auth;

    static ProfileDto pd;
    static PorteDto pdd;



    }


