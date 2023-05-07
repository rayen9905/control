package com.pfe.DTO;

import com.pfe.entities.Event;
import com.pfe.entities.Type_Evt;
import com.pfe.entities.WaveShare;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private Long IdEvent;
    private Type_Evt EtEvent;

    private Date DateEvent;
    private List<WaveDto> waves;

    static WaveDto dtt ;
    public static EventDto toDto(Event e) {
        List<WaveDto> ev = new ArrayList<>();
        List<WaveShare> l= e.getWaves();
        for (WaveShare evt:l
        ) {
            ev.add(dtt.toDto((evt)));
        }
        /*List<UserDto> ur = new ArrayList<>();
        List<User> u= p.getUsr();
        for (User utt:u
        ) {
            ur.add(dtt.toDto((utt)));
        }
        DepartementDto d = dttt.toDto(p.getDep());*/
        return EventDto.builder()
                .IdEvent(e.getIdEvent())
                .EtEvent(e.getEtEvent())
                //.DateEvent(e.getDateEvent())
                .waves(ev)
                .build();
    }

}
