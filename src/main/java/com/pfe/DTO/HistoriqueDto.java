package com.pfe.DTO;

import com.pfe.entities.*;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class HistoriqueDto {
    private Long IdHis;
    private PorteDto prt;
    private UserDto usr;

    static PorteDto dt;
    static  UserDto dtt;
    public static HistoriqueDto toDto(Historique h) {
        PorteDto pd = dt.toDto(h.getPrt());
        UserDto us = dtt.toDto(h.getUsr());
        return HistoriqueDto.builder()
                .IdHis(h.getIdHis())
                .prt(pd)
                .usr(us)
                .build();
    }
}
