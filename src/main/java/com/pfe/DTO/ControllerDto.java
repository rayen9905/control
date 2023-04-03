package com.pfe.DTO;


import com.pfe.entities.Controlleur;
import com.pfe.entities.Profile;
import com.pfe.entities.StatuCntrl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ControllerDto {
    private Long IdCont;
    private String NomCont;
    private StatuCntrl Status;
    private Long dept;

    public static ControllerDto toDto(Controlleur c) {
        return ControllerDto.builder()
                .IdCont(c.getIdCont())
                .NomCont(c.getNomCont())
                .Status(c.getStatus())
                .dept(c.getDept().getIdDep())
                .build();
    }
}
