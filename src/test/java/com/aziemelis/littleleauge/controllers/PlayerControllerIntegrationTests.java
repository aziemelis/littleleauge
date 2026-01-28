package com.aziemelis.littleleauge.controllers;

import com.aziemelis.littleleauge.TestDataUtil;
import com.aziemelis.littleleauge.domain.dto.PlayerDto;
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

    @Test
    public void testThatGetPlayerReturnsHttpStatus200WhenPlayerExists() throws Exception {
        PlayerEntity playerEntity = TestDataUtil.createTestPlayerEntity1();
        playerService.save(playerEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/players/" + playerEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetPlayerReturnsHttpStatus404WhenPlayerDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/players/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetPlayerReturnsPlayerWhenDoesExist() throws Exception {
        PlayerEntity playerEntity = TestDataUtil.createTestPlayerEntity1();
        playerService.save(playerEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/players/" + playerEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
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
    public void testThatUpdatePlayerReturnsHttpStatus200WhenPlayerExists() throws Exception {
        PlayerEntity playerEntity = TestDataUtil.createTestPlayerEntity1();
        PlayerEntity savedPlayerEntity = playerService.save(playerEntity);

        PlayerDto testPlayerDto = TestDataUtil.createTestPlayerDto1();
        String playerDtoJson = objectMapper.writeValueAsString(testPlayerDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/players/" + savedPlayerEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatUpdatePlayerReturnsHttpStatus404WhenPlayerDoesNotExist() throws Exception {
        PlayerEntity playerEntity = TestDataUtil.createTestPlayerEntity1();
        String playerDtoJson = objectMapper.writeValueAsString(playerEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/players/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatUpdatePlayerReturnsExistingPlayer() throws Exception {
        PlayerEntity playerEntity = TestDataUtil.createTestPlayerEntity1();
        PlayerEntity savedPlayerEntity = playerService.save(playerEntity);

        PlayerDto testPlayerDto = TestDataUtil.createTestPlayerDto1();
        testPlayerDto.setId(savedPlayerEntity.getId());

        String playerDtoJson = objectMapper.writeValueAsString(testPlayerDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/players/" + savedPlayerEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerDtoJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedPlayerEntity.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.firstName").value(savedPlayerEntity.getFirstName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.lastName").value(savedPlayerEntity.getLastName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.fullName").value(savedPlayerEntity.getFullName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.active").value(savedPlayerEntity.getActive())
        );
    }

    @Test
    public void testThatPutPlayerReturnsHttpStatus200WhenPlayerExists() throws Exception {
        PlayerEntity playerEntity = TestDataUtil.createTestPlayerEntity1();
        PlayerEntity savedPlayerEntity = playerService.save(playerEntity);

        PlayerDto testPlayerDto = TestDataUtil.createTestPlayerDto1();
        testPlayerDto.setFullName("Updated Full Name");
        String playerDtoJson = objectMapper.writeValueAsString(testPlayerDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/players/" + savedPlayerEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPatchPlayerUpdateExistingPlayerReturnsUpdatedPlayer() throws Exception {
        PlayerEntity playerEntity = TestDataUtil.createTestPlayerEntity1();
        PlayerEntity savedPlayerEntity = playerService.save(playerEntity);

        PlayerDto testPlayerDto = TestDataUtil.createTestPlayerDto1();
        testPlayerDto.setFullName("Updated Full Name");
        String playerDtoJson = objectMapper.writeValueAsString(testPlayerDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/players/" + savedPlayerEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerDtoJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedPlayerEntity.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.firstName").value(savedPlayerEntity.getFirstName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.lastName").value(savedPlayerEntity.getLastName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.fullName").value("Updated Full Name")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.active").value(savedPlayerEntity.getActive())
        );
    }

    @Test
    public void testThatDeletePlayerReturnsHttpStatus204WhenPlayerExists() throws Exception {
        PlayerEntity playerEntity = TestDataUtil.createTestPlayerEntity1();
        PlayerEntity savedPlayerEntity = playerService.save(playerEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/players/" + savedPlayerEntity.getId())
                        .content(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeletePlayerReturnsHttpStatus204WhenPlayerDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/players/99")
                        .content(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
