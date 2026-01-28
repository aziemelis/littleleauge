package com.aziemelis.littleleauge.controllers;

import com.aziemelis.littleleauge.domain.dto.PlayerDto;
import com.aziemelis.littleleauge.domain.entities.PlayerEntity;
import com.aziemelis.littleleauge.mappers.Mapper;
import com.aziemelis.littleleauge.services.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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

    @GetMapping(path = "/players/{id}")
    public ResponseEntity<PlayerDto> getPlayer(@PathVariable long id) {
        Optional<PlayerEntity> foundPlayer = playerService.getOne(id);
        return foundPlayer.map(playerEntity -> {
            PlayerDto playerDto = playerMapper.mapTo(playerEntity);
            return new ResponseEntity<>(playerDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/players/{id}")
    public ResponseEntity<PlayerDto> updatePlayer(@PathVariable long id, @RequestBody PlayerDto playerDto) {
        if (!playerService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        playerDto.setId(id);
        PlayerEntity playerEntity = playerMapper.mapFrom(playerDto);
        playerService.save(playerEntity);

        return new ResponseEntity<>(playerMapper.mapTo(playerEntity), HttpStatus.OK);
    }

    @PatchMapping(path = "/players/{id}")
    public ResponseEntity<PlayerDto> patchPlayer(@PathVariable long id, @RequestBody PlayerDto playerDto) {
        if (!playerService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        PlayerEntity playerEntity = playerMapper.mapFrom(playerDto);
        PlayerEntity updatedPlayer = playerService.partialUpdate(id,  playerEntity);

        return new ResponseEntity<>(playerMapper.mapTo(updatedPlayer), HttpStatus.OK);
    }

    @DeleteMapping( path = "/players/{id}")
    public ResponseEntity<PlayerDto> deletePlayer(@PathVariable long id) {
        playerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
