package com.aziemelis.littleleauge.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private Boolean active;
}
