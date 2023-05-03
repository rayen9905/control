package com.pfe.DTO;

import com.pfe.entities.Controlleur;
import com.pfe.entities.Departement;
import com.pfe.entities.Porte;
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
public class DepartementDto {
    private Long IdDep;
    private String NomDep;
    private List<ControllerDto> cntrl;
    private List<PorteDto>prt;

    static PorteDto dt;
    static ControllerDto dtT;
    public static DepartementDto toDto(Departement d) {
        List<PorteDto> pt = new ArrayList<>();
        List<Porte> p= d.getPorte();
        for (Porte ptt:p
        ) {
            pt.add(dt.toDto((ptt)));
        }
        List<ControllerDto> cn = new ArrayList<>();
        List<Controlleur> cnt = d.getCntrls();
        for (Controlleur cntt:cnt
        ) {
            cn.add(dtT.toDto((cntt)));
        }
        return DepartementDto.builder()
                .IdDep(d.getIdDep())
                .NomDep(d.getNomDep())
                .prt(pt)
                .cntrl(cn)
                .build();
    }
}
