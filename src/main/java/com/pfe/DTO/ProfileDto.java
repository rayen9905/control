package com.pfe.DTO;

import com.pfe.entities.Profile;
import com.pfe.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {

    private Long IdProf;
    private String NomProfile;
    private List<UserDto> usr;
    static UserDto dt;
    public static ProfileDto toDto(Profile p) {
        List<UserDto> us= new ArrayList<>();
        List<User> u= p.getUsr();
        for (User uu:u
             ) {
            us.add(dt.toDto(uu));
        }
        return ProfileDto.builder()
                .IdProf(p.getIdProf())
                .NomProfile(p.getNomProfile())
                .usr(us)
                .build();
    }
}
