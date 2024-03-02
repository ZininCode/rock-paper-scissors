package se.cygni.cygnitask.helper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import se.cygni.cygnitask.model.Game;
import se.cygni.cygnitask.repository.GameRepository;


/**
 * Resolve the game result when both of the players mke their moves and return game result a draw or a winner.
 * Date: 11.07.2023
 *
 * @author Nikolay Zinin
 */
@Component
@AllArgsConstructor
@Slf4j
public class GameResolver {
    private final GameRepository repository;
    private final GameResultHelper gameResultHelper;

    public void resolveGame(Game game) {
        log.debug("Both player made moves for game {}", game.getId());
        if (game.getPlayer1Move().equals(game.getPlayer2Move())) {
            repository.makeDraw(game.getId());
            log.debug("Game {} finished with draw", game.getId());
        } else {
            boolean firstPlayerWins = gameResultHelper.matchResult(game.getPlayer1Move(), game.getPlayer2Move());
            if (firstPlayerWins) {
                repository.makePlayer1Wins(game.getId());
                log.debug("Game {} finished, player {} wins", game.getId(), game.getPlayer1());
            } else {
                repository.makePlayer2Wins(game.getId());
                log.debug("Game {} finished, player {} wins", game.getId(), game.getPlayer2());
            }
        }
    }
}
