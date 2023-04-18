package com.pfe.DTO;

import com.pfe.entities.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PorteDto {
    private Long IdPorte;
    private String NomPorte;
    private Long cntrl;
    private TypePorte Type;

    public static PorteDto toDto(Porte p) {
        return PorteDto.builder()
                .IdPorte(p.getIdPorte())
                .NomPorte(p.getNomPorte())
               // .cntrl(p.getCntrl().getIdCont())
                .Type(p.getType())
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
