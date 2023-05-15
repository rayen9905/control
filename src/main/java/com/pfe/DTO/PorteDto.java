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



}

