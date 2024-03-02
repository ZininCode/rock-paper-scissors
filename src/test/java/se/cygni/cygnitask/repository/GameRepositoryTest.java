package se.cygni.cygnitask.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import se.cygni.cygnitask.exception.GameNotFoundException;
import se.cygni.cygnitask.model.Game;
import se.cygni.cygnitask.rest.api.gameenum.GameStatus;
import se.cygni.cygnitask.rest.api.gameenum.MoveEnum;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 * Date: 15.07.2023
 *
 * @author Nikolay Zinin
 */
@ExtendWith(MockitoExtension.class)
class GameRepositoryTest {

    private GameRepository repository;
    @Mock
    Map<UUID, Game> gameMap;

    @BeforeEach
    public void initService() {
        repository = new GameRepository();
        ReflectionTestUtils.setField(repository, "gameMap", gameMap);
    }

    @Test
    void addGameTest() {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .build();
        repository.addGame(game);
        Mockito.verify(gameMap, times(1)).put(game.getId(), game);
    }

    @Test
    void findGameTest() {
        UUID gameId = UUID.randomUUID();
        repository.findGame(gameId);
        Mockito.verify(gameMap, times(1)).get(gameId);
    }

    @Test
    void addPlayer2ToGamePlayerAndStatusTest() {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .build();
        Mockito.when(gameMap.get(gameId)).thenReturn(game);
        String player = "Bob";
        repository.addPlayerToGame(gameId, player);
        assertEquals(game.getPlayer2(), "Bob");
        assertEquals(game.getStatus(), GameStatus.IN_PROGRESS);
    }

    @Test
    void player1MoveTest() {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .player1("Bob")
                .player2("Cat")
                .status(GameStatus.IN_PROGRESS)
                .build();
        Mockito.when(gameMap.get(gameId)).thenReturn(game);
        repository.player1Move(gameId, MoveEnum.ROCK);
        assertEquals(game.getPlayer1Move(), MoveEnum.ROCK);
    }

    @Test
    void player2MoveTest() {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .player1("Bob")
                .player2("Cat")
                .status(GameStatus.IN_PROGRESS)
                .build();
        Mockito.when(gameMap.get(gameId)).thenReturn(game);
        repository.player2Move(gameId, MoveEnum.ROCK);
        assertEquals(game.getPlayer2Move(), MoveEnum.ROCK);
    }

    @Test
    void makeDrawTest() {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .player1("Bob")
                .player2("Cat")
                .player1Move(MoveEnum.ROCK)
                .player2Move(MoveEnum.ROCK)
                .status(GameStatus.IN_PROGRESS)
                .build();

        Mockito.when(gameMap.get(gameId)).thenReturn(game);
        repository.makeDraw(gameId);
        assertEquals(game.getStatus(), GameStatus.DRAW);
    }

    @Test
    void makePlayer1WinsTest() {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .player1("Bob")
                .player2("Cat")
                .player1Move(MoveEnum.PAPER)
                .player2Move(MoveEnum.ROCK)
                .status(GameStatus.IN_PROGRESS)
                .build();

        Mockito.when(gameMap.get(gameId)).thenReturn(game);
        repository.makePlayer1Wins(gameId);
        assertTrue(game.getStatus().equals(GameStatus.HAS_WINNER) && game.getWinner().equals("Bob"));
    }

    @Test
    void makePlayer2WinsTest() {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .player1("Bob")
                .player2("Cat")
                .player1Move(MoveEnum.ROCK)
                .player2Move(MoveEnum.PAPER)
                .status(GameStatus.IN_PROGRESS)
                .build();

        Mockito.when(gameMap.get(gameId)).thenReturn(game);
        repository.makePlayer2Wins(gameId);
        assertTrue(game.getStatus().equals(GameStatus.HAS_WINNER) && game.getWinner().equals("Cat"));
    }
}
