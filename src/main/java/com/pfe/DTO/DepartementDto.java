package com.pfe.DTO;

import com.pfe.entities.Controlleur;
import com.pfe.entities.Departement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartementDto {
    private Long IdDep;
    private String NomDep;

    public static DepartementDto toDto(Departement d) {
        return DepartementDto.builder()
                .IdDep(d.getIdDep())
                .NomDep(d.getNomDep())
                .build();
    }
}
