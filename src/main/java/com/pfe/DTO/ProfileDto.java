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
    public static ProfileDto toDto(Profile p) {
        return ProfileDto.builder()
                .IdProf(p.getIdProf())
                .NomProfile(p.getNomProfile())
                .build();
    }
}
