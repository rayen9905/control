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

}
