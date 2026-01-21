package com.aziemelis.littleleauge.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "player")
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_id_seq")
    private Long id;

    private String firstName;
    private String lastName;
    private String fullName;
    private boolean isActive;
}
