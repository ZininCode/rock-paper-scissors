package se.cygni.cygnitask.service;
import se.cygni.cygnitask.dto.GameDto;
import se.cygni.cygnitask.exception.*;
import se.cygni.cygnitask.rest.api.gameenum.MoveEnum;

import java.util.UUID;

/**
 * Create a game and two players join the game.
 *
 * Date: 07.07.2023
 *
 * @author Nikolay Zinin
 */
public interface GameService {


    UUID createGame(String playerName);


    void joinGame(UUID gameId, String playerName) throws GameNotFoundException, JoinGameSamePlayerNameException, JoinFullGameException, GameNotInProgressException;

    void makeMove(UUID gameId, String playerName, MoveEnum move) throws GameNotFoundException, GameNotInProgressException, PlayerAlreadyMadeMoveException;

    GameDto getGameResult(UUID gameId) throws GameNotFoundException;
}
