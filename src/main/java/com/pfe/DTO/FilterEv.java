package com.pfe.DTO;

import com.pfe.entities.Type_Evt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterEv {
    private LocalDate dateDeb;
    private LocalDate dateFin;
    private LocalTime timeDeb;
    private LocalTime timeFin;
    private Type_Evt typeEv;
}
