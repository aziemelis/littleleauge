package com.aziemelis.littleleauge.services.impl;

import com.aziemelis.littleleauge.domain.entities.PlayerEntity;
import com.aziemelis.littleleauge.repositories.PlayerRepository;
import com.aziemelis.littleleauge.services.PlayerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        return StreamSupport.stream(playerRepository
                .findAll()
                .spliterator(),
                false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PlayerEntity> getOne(Long id) {
        return playerRepository.findById(id);
    }

    @Override
    public boolean exists(Long id) {
        return playerRepository.existsById(id);
    }

    @Override
    public PlayerEntity partialUpdate(Long id, PlayerEntity playerEntity) {
        playerEntity.setId(id);

        return playerRepository.findById(id).map(existingPlayer -> {
            Optional.ofNullable(playerEntity.getFirstName()).ifPresent(existingPlayer::setFirstName);
            Optional.ofNullable(playerEntity.getLastName()).ifPresent(existingPlayer::setLastName);
            Optional.ofNullable(playerEntity.getFullName()).ifPresent(existingPlayer::setFullName);
            Optional.ofNullable(playerEntity.getActive()).ifPresent(existingPlayer::setActive);
            return playerRepository.save(existingPlayer);
        }).orElseThrow(() -> new RuntimeException("Player with id " + id + " not found!"));
    }

    @Override
    public void delete(Long id) {
        playerRepository.deleteById(id);
    }
}
