package com.pfe.DTO;

import com.pfe.entities.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PorteDto {
    private Long IdPorte;
    private String NomPorte;
    private List<LecteurDto> lec;
    private List<UserDto> usr;
    private TypePorte Type;
    private String idwave;
    private DepartementDto dep;

    static LecteurDto dt;
    static UserDto dtt;
    static DepartementDto dttt;



    public static PorteDto toDto(Porte p) {

        List<LecteurDto> le = new ArrayList<>();
        List<Lecteur> l= new ArrayList<>();
        l=p.getLecteur();
        for (Lecteur ltt:l
        ) {
            le.add(dt.toDto((ltt)));
        }
        List<UserDto> ur = new ArrayList<>();
        //List<User> u= p.getUsr();
        for (User utt:p.getUsr()
        ) {
            ur.add(dtt.toDto((utt)));
        }
        DepartementDto d ;
        return PorteDto.builder()
                .IdPorte(p.getIdPorte())
                .NomPorte(p.getNomPorte())
                //.lec(le)
                //.usr(ur)
                //.dep(d)
                .Type(p.getType())
                .idwave(p.getWsh().getAdresse())
                .build();
    }
    public static PorteDto toDtoop(Optional<Porte> p) {

        return PorteDto.builder()
                .IdPorte(p.get().getIdPorte())
                .NomPorte(p.get().getNomPorte())
                // .cntrl(p.getCntrl().getIdCont())
                .Type(p.get().getType())
                .build();
    }
}

