package com.aziemelis.littleleauge.services;

import com.aziemelis.littleleauge.domain.entities.PlayerEntity;

import java.util.List;
import java.util.Optional;

public interface PlayerService {
    PlayerEntity save(PlayerEntity playerEntity);

    List<PlayerEntity> getAll();

    Optional<PlayerEntity> getOne(Long id);

    boolean exists(Long id);

    PlayerEntity partialUpdate(Long id, PlayerEntity playerEntity);

    void delete(Long id);
}
