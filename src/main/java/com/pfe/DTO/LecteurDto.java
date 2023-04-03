package com.pfe.DTO;

import com.pfe.entities.Lecteur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LecteurDto {
    private Long IdLecteur;
    private String IpAdresse;
    private Long prt;

    public static LecteurDto toDto(Lecteur l) {
        return LecteurDto.builder()
                .IdLecteur(l.getIdLecteur())
                .IpAdresse(l.getIpAdresse())
                .prt(l.getPrt().getIdPorte())
                .build();
    }
}
