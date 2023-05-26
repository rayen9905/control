package com.pfe.DTO;

import com.pfe.entities.*;
import lombok.*;

import java.util.List;


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
    private Type_Sens Type;
    private String idwave;
    private DepartementDto dep;

    static LecteurDto dt;
    static UserDto dtt;
    static DepartementDto dttt;



}

