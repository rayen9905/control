package com.pfe.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name ="Event")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Event {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long IdEvent;
    @Enumerated(EnumType.STRING)
    @Column(name = "Et_Event", length = 100)
    private Type_Evt EtEvent;
    @Column(name = "Date_Event", length = 100)
    private LocalDate DateEvent;
    @Column(name = "Time_Event", length = 100)
    private LocalTime TimeEvent;
    @ManyToMany
    @JsonIgnoreProperties("events")
    private List<WaveShare> waves;

}
