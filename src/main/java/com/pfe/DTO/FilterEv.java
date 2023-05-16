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
    private String dateDeb;
    private String dateFin;
    private String timeDeb;
    private String timeFin;
    private String typeEv;
}
