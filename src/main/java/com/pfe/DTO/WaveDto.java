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
public class WaveDto {
    private String adresse;
    private String status;
    private PorteDto prt;
    private List<EventDto> edt;
    static PorteDto dt;
    static EventDto dtt;
    public static WaveDto toDto(WaveShare w) {
        PorteDto pd= dt.toDto(w.getPrt());
        List<EventDto> ev = new ArrayList<>();
        List<Event> l= w.getEvents();
        for (Event evt:l
        ) {
            ev.add(dtt.toDto((evt)));
        }
        return WaveDto.builder()
                .adresse(w.getAdresse())
                .status(w.getStatus())
                .prt(pd)
                .edt(ev)
                .build();
    }
}
