package com.pfe.DTO;

import com.pfe.entities.*;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HistoriqueDto {
    private LocalDate date;
    private int acc;
    private int den;

}
