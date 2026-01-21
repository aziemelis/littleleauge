package com.aziemelis.littleleauge;

import com.aziemelis.littleleauge.domain.entities.PlayerEntity;

public class TestDataUtil {
    private TestDataUtil() {}

    public static PlayerEntity createTestPlayerEntity1() {
        return PlayerEntity.builder()
                .firstName("Anthony")
                .lastName("Edwards")
                .fullName("Anthony Edwards")
                .active(true)
                .build();
    }

    public static PlayerEntity createTestPlayerEntity2() {
        return PlayerEntity.builder()
                .firstName("Nikola")
                .lastName("Jokic")
                .fullName("Nikola Jokic")
                .active(true)
                .build();
    }

    public static PlayerEntity createTestPlayerEntity3() {
        return PlayerEntity.builder()
                .firstName("Kobe")
                .lastName("Bryant")
                .fullName("Kobe Bryant")
                .active(false)
                .build();
    }
}
