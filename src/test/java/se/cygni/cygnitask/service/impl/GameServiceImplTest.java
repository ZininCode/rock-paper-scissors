package se.cygni.cygnitask.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import se.cygni.cygnitask.dto.GameDto;
import se.cygni.cygnitask.exception.GameNotFoundException;
import se.cygni.cygnitask.exception.GameNotInProgressException;
import se.cygni.cygnitask.exception.PlayerAlreadyMadeMoveException;
import se.cygni.cygnitask.helper.GameResolver;
import se.cygni.cygnitask.helper.GameResultHelper;
import se.cygni.cygnitask.mapper.GameMapper;
import se.cygni.cygnitask.mapper.GameMapperImpl;
import se.cygni.cygnitask.model.Game;
import se.cygni.cygnitask.repository.GameRepository;
import se.cygni.cygnitask.rest.api.gameenum.GameStatus;
import se.cygni.cygnitask.rest.api.gameenum.MoveEnum;
import se.cygni.cygnitask.service.GameService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;

/**
 * Date: 15.07.2023
 *
 * @author Nikolay Zinin
 */
@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {
    @Mock
    private GameRepository repository;
    @Mock
    private GameResolver gameResolver;
    private GameService gameService;

    @BeforeEach
    public void initService() {
        GameResultHelper gameResultHelper = new GameResultHelper();
        GameMapper gameMapper = new GameMapperImpl();
        gameService = new GameServiceImpl(repository, gameResolver, gameMapper);
    }

    @Test
    void player1MakesFirstMoveTest() throws GameNotFoundException, PlayerAlreadyMadeMoveException, GameNotInProgressException {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .player1("Bob")
                .player2("Cat")
                .status(GameStatus.IN_PROGRESS)
                .build();
        Mockito.when(repository.findGame(gameId)).thenReturn(Optional.ofNullable(game));
        gameService.makeMove(gameId, "Bob", MoveEnum.ROCK);
        Mockito.verify(repository, times(1)).player1Move(gameId, MoveEnum.ROCK);
    }


    @Test
    void player2MakesFirstMoveGameParametersTest() throws GameNotFoundException, PlayerAlreadyMadeMoveException, GameNotInProgressException {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .player1("Bob")
                .player2("Cat")
                .status(GameStatus.IN_PROGRESS)
                .build();
        Mockito.when(repository.findGame(gameId)).thenReturn(Optional.ofNullable(game));
        gameService.makeMove(gameId, "Cat", MoveEnum.ROCK);
        Mockito.verify(repository, times(1)).player2Move(gameId, MoveEnum.ROCK);
    }

    @Test
    void player2MakesSecondMoveSolveGameTest()
            throws GameNotFoundException, PlayerAlreadyMadeMoveException, GameNotInProgressException {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .player1("Bob")
                .player2("Cat")
                .player1Move(MoveEnum.ROCK)
                .status(GameStatus.IN_PROGRESS)
                .build();

        Mockito.when(repository.findGame(gameId)).thenReturn(Optional.ofNullable(game));
        doAnswer(invocation -> {
            MoveEnum arg1 = invocation.getArgument(1);
            game.setPlayer2Move(arg1);
            return null;
        }).when(repository).player2Move(any(UUID.class), any(MoveEnum.class));

        gameService.makeMove(gameId, "Cat", MoveEnum.PAPER);

        Mockito.verify(gameResolver, times(1)).resolveGame(game);
    }

    @Test
    void getGameResultWhenInProgress() throws GameNotFoundException {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .player1("Bob")
                .player2("Cat")
                .player1Move(MoveEnum.ROCK)
                .status(GameStatus.IN_PROGRESS)
                .build();
        Mockito.when(repository.findGame(gameId)).thenReturn(Optional.ofNullable(game));
        GameDto response = gameService.getGameResult(gameId);
        assertEquals(response.getStatus(), GameStatus.IN_PROGRESS);
    }

    @Test
    void getGameResultWhenInHasWinner() throws GameNotFoundException {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .player1("Bob")
                .player2("Cat")
                .player1Move(MoveEnum.ROCK)
                .player2Move(MoveEnum.PAPER)
                .status(GameStatus.HAS_WINNER)
                .winner("Cat")
                .build();

        Mockito.when(repository.findGame(gameId)).thenReturn(Optional.ofNullable(game));
        GameDto response = gameService.getGameResult(gameId);
        assertEquals(response.getStatus(), GameStatus.HAS_WINNER);
    }
}
