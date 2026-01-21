package com.aziemelis.littleleauge.repositories;

import com.aziemelis.littleleauge.domain.entities.PlayerEntity;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<PlayerEntity, Long> {
}
