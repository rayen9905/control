package com.pfe.DTO;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DeviceDto {
    private LocalDate date;
    private int diconnected;
}
