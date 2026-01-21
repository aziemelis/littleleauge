package com.aziemelis.littleleauge.controllers;

import com.aziemelis.littleleauge.TestDataUtil;
import com.aziemelis.littleleauge.domain.entities.PlayerEntity;
import com.aziemelis.littleleauge.services.PlayerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PlayerControllerIntegrationTests {

    private final PlayerService playerService;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public PlayerControllerIntegrationTests(PlayerService playerService, MockMvc mockMvc) {
        this.playerService = playerService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreatePlayersSuccessfullyReturnsHttp201Created() throws Exception {
        PlayerEntity playerEntity = TestDataUtil.createTestPlayerEntity1();
        playerEntity.setId(null);
        String playerJson = objectMapper.writeValueAsString(playerEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );

    }

    @Test
    public void testThatCreatePlayersSuccessfullyReturnsSavedPlayer() throws Exception {
        PlayerEntity playerEntity = TestDataUtil.createTestPlayerEntity1();
        playerEntity.setId(null);
        String playerJson = objectMapper.writeValueAsString(playerEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.firstName").value("Anthony")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.lastName").value("Edwards")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.fullName").value("Anthony Edwards")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.active").value(true)
        );
    }

    @Test
    public void testThatListPlayersReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/players")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatListPlayersReturnsListPlayers() throws Exception {
        PlayerEntity testPlayerEntity = TestDataUtil.createTestPlayerEntity1();
        playerService.save(testPlayerEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/players")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].firstName").value("Anthony")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].lastName").value("Edwards")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].fullName").value("Anthony Edwards")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].active").value(true)
        );
    }
}
