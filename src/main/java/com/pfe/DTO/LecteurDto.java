package com.pfe.DTO;

import com.pfe.entities.Lecteur;
import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LecteurDto {
    private Long IdLecteur;
    private String IpAdresse;
    private PorteDto prt;

    static PorteDto dt;
    public static LecteurDto toDto(Lecteur l) {
        PorteDto pd= dt.toDto(l.getPrt());

        return LecteurDto.builder()
                .IdLecteur(l.getIdLecteur())
                .IpAdresse(l.getIpAdresse())
                .prt(pd)
                .build();
    }
}
