package com.pfe.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Refreshtoken {
    @Id
    @GeneratedValue
    public Integer id;

    @Column(unique = true)
    public String token;

    @Enumerated(EnumType.STRING)
    public RefreshType tokenType = RefreshType.BEARER;

    public boolean revoked;

    public boolean expired;

    @OneToOne
    //@JoinColumn(name = "ref")
    public User user;
}
