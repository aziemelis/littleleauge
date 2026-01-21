package com.aziemelis.littleleauge.mappers;

public interface Mapper<Entity, Dto> {
    Dto mapTo(Entity entity);

    Entity mapFrom(Dto dto);
}
