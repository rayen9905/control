package com.pfe.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterEv2 {
    private String dateDeb;
    private String dateFin;
    private String timeDeb;
    private String timeFin;
    private String etat;
    private Long dep;
    private String cin;

}
