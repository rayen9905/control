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

}
