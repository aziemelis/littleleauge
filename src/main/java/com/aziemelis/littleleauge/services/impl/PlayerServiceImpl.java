package com.aziemelis.littleleauge.services.impl;

import com.aziemelis.littleleauge.domain.entities.PlayerEntity;
import com.aziemelis.littleleauge.repositories.PlayerRepository;
import com.aziemelis.littleleauge.services.PlayerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public PlayerEntity save(PlayerEntity playerEntity) {
        return playerRepository.save(playerEntity);
    }

    @Override
    public List<PlayerEntity> getAll() {
        return List.of();
    }

    @Override
    public Optional<PlayerEntity> getOne(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean exists(Long id) {
        return false;
    }

    @Override
    public PlayerEntity partialUpdate(PlayerEntity playerEntity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
