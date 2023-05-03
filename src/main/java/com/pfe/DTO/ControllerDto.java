package com.pfe.DTO;


import com.pfe.entities.*;
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
public class ControllerDto {
    private Long IdCont;
    private String NomCont;
    private StatuCntrl Status;
    private DepartementDto dept;

    static DepartementDto dt;
    public static ControllerDto toDto(Controlleur c) {
        Departement d = c.getDept();
        DepartementDto dtt = dt.toDto(d);
        return ControllerDto.builder()
                .IdCont(c.getIdCont())
                .NomCont(c.getNomCont())
                .Status(c.getStatus())
                .dept(dtt)
                .build();
    }
    //aaa 3asba 3le sormek
}
