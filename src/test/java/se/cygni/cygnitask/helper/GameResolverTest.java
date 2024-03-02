package se.cygni.cygnitask.helper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import se.cygni.cygnitask.model.Game;
import se.cygni.cygnitask.repository.GameRepository;
import se.cygni.cygnitask.rest.api.gameenum.GameStatus;
import se.cygni.cygnitask.rest.api.gameenum.MoveEnum;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

/**
 * Date: 15.07.2023
 *
 * @author Nikolay Zinin
 */
@ExtendWith(MockitoExtension.class)
class GameResolverTest {
    @Mock
    private GameRepository repository;
    private GameResolver gameResolver;

    @BeforeEach
    public void initService() {
        GameResultHelper gameResultHelper = new GameResultHelper();
        gameResolver = new GameResolver(repository, gameResultHelper);
    }

    @Test
    void resolveGameWhenOnePlayerWins() {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .player1("Bob")
                .player2("Cat")
                .player1Move(MoveEnum.ROCK)
                .player2Move(MoveEnum.PAPER)
                .status(GameStatus.IN_PROGRESS)
                .winner("Cat")
                .build();

        gameResolver.resolveGame(game);
        Mockito.verify(repository, times(1)).makePlayer2Wins(game.getId());
    }

    @Test
    void resolveGameWhenResultIsDraw() {
        UUID gameId = UUID.randomUUID();
        Game game = Game
                .builder()
                .id(gameId)
                .player1("Bob")
                .player2("Cat")
                .player1Move(MoveEnum.ROCK)
                .player2Move(MoveEnum.ROCK)
                .status(GameStatus.IN_PROGRESS)
                .winner("Cat")
                .build();

        gameResolver.resolveGame(game);
        Mockito.verify(repository, times(1)).makeDraw(game.getId());
    }
}
