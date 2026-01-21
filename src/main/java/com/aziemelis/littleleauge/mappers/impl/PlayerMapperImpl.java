package com.aziemelis.littleleauge.mappers.impl;

import com.aziemelis.littleleauge.domain.dto.PlayerDto;
import com.aziemelis.littleleauge.domain.entities.PlayerEntity;
import com.aziemelis.littleleauge.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapperImpl implements Mapper<PlayerEntity, PlayerDto> {

    private final ModelMapper modelMapper;

    public PlayerMapperImpl() {
        this.modelMapper = new ModelMapper();
    }

    @Override
    public PlayerDto mapTo(PlayerEntity playerEntity) {
        return modelMapper.map(playerEntity, PlayerDto.class);
    }

    @Override
    public PlayerEntity mapFrom(PlayerDto playerDto) {
        return modelMapper.map(playerDto, PlayerEntity.class);
    }
}
