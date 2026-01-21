package com.aziemelis.littleleauge.controllers;

import com.aziemelis.littleleauge.domain.dto.PlayerDto;
import com.aziemelis.littleleauge.domain.entities.PlayerEntity;
import com.aziemelis.littleleauge.mappers.Mapper;
import com.aziemelis.littleleauge.services.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PlayerController {

    private final PlayerService playerService;
    private final Mapper<PlayerEntity, PlayerDto> playerMapper;

    public PlayerController(PlayerService playerService, Mapper<PlayerEntity, PlayerDto> playerMapper) {
        this.playerService = playerService;
        this.playerMapper = playerMapper;
    }

    @PostMapping(path = "/players")
    public ResponseEntity<PlayerDto> createPlayer(@RequestBody PlayerDto playerDto) {
        PlayerEntity playerEntity = playerMapper.mapFrom(playerDto);
        PlayerEntity savedPlayerEntity = playerService.save(playerEntity);
        return new ResponseEntity<>(playerMapper.mapTo(savedPlayerEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/players")
    public List<PlayerDto> getAllPlayers() {
        List<PlayerEntity> playerEntities = playerService.getAll();
        return playerEntities.stream()
                .map(playerMapper::mapTo)
                .collect(Collectors.toList());
    }
}
