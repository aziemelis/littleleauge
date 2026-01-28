package com.aziemelis.littleleauge.repositories;

import com.aziemelis.littleleauge.TestDataUtil;
import com.aziemelis.littleleauge.domain.entities.PlayerEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PlayerEntityRepositoryIntegrationTests {
    private final PlayerRepository underTest;

    @Autowired
    public PlayerEntityRepositoryIntegrationTests(PlayerRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatPlayerCanBeCreatedAndRecalled() {
        PlayerEntity playerEntity = TestDataUtil.createTestPlayerEntity1();
        underTest.save(playerEntity);

        Optional<PlayerEntity> result = underTest.findById(playerEntity.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(playerEntity);
    }

    @Test
    public void testThatMultiplePlayersCanBeCreatedAndRecalled() {
        PlayerEntity playerEntity1 = TestDataUtil.createTestPlayerEntity1();
        underTest.save(playerEntity1);
        PlayerEntity playerEntity2 = TestDataUtil.createTestPlayerEntity2();
        underTest.save(playerEntity2);
        PlayerEntity playerEntity3 = TestDataUtil.createTestPlayerEntity3();
        underTest.save(playerEntity3);

        Iterable<PlayerEntity> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(playerEntity1, playerEntity2, playerEntity3);
    }

    @Test
    public void testThatPlayerCanBeUpdated() {
        PlayerEntity playerEntity1 = TestDataUtil.createTestPlayerEntity1();
        underTest.save(playerEntity1);

        playerEntity1.setFirstName("UPDATED");
        underTest.save(playerEntity1);

        Optional<PlayerEntity> result = underTest.findById(playerEntity1.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(playerEntity1);
    }

    @Test
    public void testThatPlayerCanBeDeleted() {
        PlayerEntity playerEntity1 = TestDataUtil.createTestPlayerEntity1();
        underTest.save(playerEntity1);
        underTest.deleteById(playerEntity1.getId());

        Optional<PlayerEntity> result = underTest.findById(playerEntity1.getId());
        assertThat(result).isEmpty();
    }
}
