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

}
