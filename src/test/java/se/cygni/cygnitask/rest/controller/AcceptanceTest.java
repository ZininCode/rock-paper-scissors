package se.cygni.cygnitask.rest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import se.cygni.cygnitask.exception.GameNotFoundException;
import se.cygni.cygnitask.exception.GameNotInProgressException;
import se.cygni.cygnitask.exception.JoinFullGameException;
import se.cygni.cygnitask.exception.JoinGameSamePlayerNameException;
import se.cygni.cygnitask.model.Game;
import se.cygni.cygnitask.repository.GameRepository;
import se.cygni.cygnitask.rest.api.gameenum.GameStatus;
import se.cygni.cygnitask.rest.api.gameenum.MoveEnum;
import se.cygni.cygnitask.rest.api.request.CreateGameRequest;
import se.cygni.cygnitask.rest.api.request.JoinGameRequest;
import se.cygni.cygnitask.rest.api.request.MoveRequest;
import se.cygni.cygnitask.rest.api.response.GameCreateResponse;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * Date: 25.01.2024
 *
 * @author Nikolay Zinin
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)


public class AcceptanceTest {

    @LocalServerPort
    int randomServerPort;
    private final RestTemplate restTemplate = new RestTemplate();
    private String url;

    @Autowired
    private GameRepository gameRepository;
    private Game gameOnePlayerJoined;
    private Game gameTwoPlayersJoined;

    @BeforeEach
    void setUp() {
        url = "http://localhost:" + randomServerPort + "/api/games";
      gameOnePlayerJoined = Game.builder()
                .id(UUID.randomUUID())
                .player1("Bob")
                .status(GameStatus.IN_PROGRESS)
                .build();
       gameTwoPlayersJoined = Game.builder()
                .id(UUID.randomUUID())
                .player1("Bob")
                .player2("Cat")
                .status(GameStatus.IN_PROGRESS)
                .build();
        gameRepository.addGame(gameOnePlayerJoined);
        gameRepository.addGame(gameTwoPlayersJoined);
    }
    @Test
    void testCreateGame() {
        CreateGameRequest request = new CreateGameRequest();
        request.setName("Bob");
        ResponseEntity<GameCreateResponse> responseEntity  = restTemplate.postForEntity(url, request, GameCreateResponse.class);
        GameCreateResponse response = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(response);
        assertNotNull(response.getGameId());
        assertTrue(response.getGameId() instanceof UUID, "gameId should be of type UUID");
    }
    @Test
    void testSecondPlayerJoinsGame() throws JoinFullGameException, GameNotInProgressException, JoinGameSamePlayerNameException, GameNotFoundException {
        String gameId = String.valueOf(gameOnePlayerJoined.getId());
        url = url + "/" + gameId + "/join";
        CreateGameRequest request = new CreateGameRequest();
        request.setName("Cat");
        ResponseEntity<JoinGameRequest> responseEntity  = restTemplate.postForEntity(url, request, JoinGameRequest.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        String player2 = gameOnePlayerJoined.getPlayer2();
        assertEquals(player2,"Cat");
    }
    @Test
    void playGameWithDrawAsResult()  {
        String gameId = String.valueOf(gameTwoPlayersJoined.getId());
        url = url + "/" + gameId + "/move";
        //player1 makes a move:
        MoveRequest request = new MoveRequest();
        request.setName("Bob");
        request.setMove(MoveEnum.ROCK);
        ResponseEntity<JoinGameRequest> responseEntity  = restTemplate.postForEntity(url, request, JoinGameRequest.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        GameStatus gameStatus = gameTwoPlayersJoined.getStatus();
        assertEquals(gameStatus, GameStatus.IN_PROGRESS);
        //player2 makes a move:
        request = new MoveRequest();
        request.setName("Cat");
        request.setMove(MoveEnum.ROCK);
        responseEntity  = restTemplate.postForEntity(url, request, JoinGameRequest.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        gameStatus = gameTwoPlayersJoined.getStatus();
        assertEquals(gameStatus, GameStatus.DRAW);
    }

    @Test
    void playGameWithOnePlayerWins()  {
        String gameId = String.valueOf(gameTwoPlayersJoined.getId());
        url = url + "/" + gameId + "/move";
        //player1 makes a move:
        MoveRequest request = new MoveRequest();
        request.setName("Bob");
        request.setMove(MoveEnum.ROCK);
        ResponseEntity<JoinGameRequest> responseEntity  = restTemplate.postForEntity(url, request, JoinGameRequest.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        GameStatus gameStatus = gameTwoPlayersJoined.getStatus();
        assertEquals(gameStatus, GameStatus.IN_PROGRESS);
        //player2 makes a move:
        request = new MoveRequest();
        request.setName("Cat");
        request.setMove(MoveEnum.PAPER);
        responseEntity  = restTemplate.postForEntity(url, request, JoinGameRequest.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        gameStatus = gameTwoPlayersJoined.getStatus();
        assertEquals(gameStatus, GameStatus.HAS_WINNER);
        String winner = gameTwoPlayersJoined.getWinner();
        assertEquals(winner, "Cat");
    }
}
