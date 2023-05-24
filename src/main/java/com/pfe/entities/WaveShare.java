package com.pfe.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name ="Waveshare")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WaveShare {
    @Id
    private String Adresse;
    @Column(name = "Status", length = 100)
    private String Status;
    @Column(name = "Name_Device", length = 100)
    private String NameDevice;
    @Column(name = "Date_Status")
    private LocalDate DateStatus;
    @OneToOne(mappedBy = "wsh")
    private Porte prt;
}
