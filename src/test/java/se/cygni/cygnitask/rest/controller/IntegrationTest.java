package se.cygni.cygnitask.rest.controller;

import org.json.JSONObject;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import se.cygni.cygnitask.mapper.GameMapper;
import se.cygni.cygnitask.rest.api.gameenum.MoveEnum;
import se.cygni.cygnitask.service.GameService;

/**
 * Date: 22.08.2023
 *
 * @author Nikolay Zinin
 */

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WebMvcTest(GameController.class)
class IntegrationTest {

    @MockBean
    GameService gameService;
    @MockBean
    GameMapper gameMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createGameIntegrationTest() throws Exception {
        Mockito.when(gameService.createGame("Bob")).thenReturn(UUID.randomUUID());
        JSONObject json = new JSONObject();
        json.put("name", "Bob");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/games/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON).content(String.valueOf(json)))
                .andExpect(status().isOk());
        verify(gameService).createGame("Bob");
    }

    @Test
    void joinGameIntegrationTest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("name", "Cat");
        UUID uuid = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/games/{uuid}/join", uuid)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON).content(String.valueOf(json)))
                .andExpect(status().isOk());
        verify(gameService).joinGame(uuid, "Cat");
    }

    @Test
    void postMoveIntegrationTest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("name", "Bob");
        json.put("move", MoveEnum.PAPER);
        UUID uuid = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/games/{gameId}/move", uuid)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON).content(String.valueOf(json)))
                .andExpect(status().isOk());
        verify(gameService).makeMove(uuid, "Bob", MoveEnum.PAPER);
    }

    @Test
    void getGameResultIntegrationTest() throws Exception {
        UUID uuid = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/games/{gameId}", uuid)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(gameService).getGameResult(uuid);
    }
}
