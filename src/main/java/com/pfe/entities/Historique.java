package com.pfe.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Historique")
public class Historique {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)//Auto increment
private Long IdHis;
@ManyToOne
@JoinColumn(name = "IdPorte")
private Porte prt;
@ManyToOne
@JoinColumn(name = "IdUser")
private User usr;


    public Historique(Porte p, User u) {
        prt=p;
        usr=u;
    }
}
