package se.cygni.cygnitask.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.cygni.cygnitask.dto.GameDto;
import se.cygni.cygnitask.exception.*;
import se.cygni.cygnitask.helper.GameResolver;
import se.cygni.cygnitask.mapper.GameMapper;
import se.cygni.cygnitask.rest.api.gameenum.GameStatus;
import se.cygni.cygnitask.model.Game;
import se.cygni.cygnitask.repository.GameRepository;
import se.cygni.cygnitask.rest.api.gameenum.MoveEnum;
import se.cygni.cygnitask.service.GameService;

import java.util.UUID;

/**
 * Date: 07.07.2023
 *
 * @author Nikolay Zinin
 */
@Service
@AllArgsConstructor
@Slf4j

public class GameServiceImpl implements GameService {
    private final GameRepository repository;
    private final GameResolver gameResolver;
    private final GameMapper gameMapper;

    @Override
    public UUID createGame(String playerName) {
        Game game = Game.builder()
                .id(UUID.randomUUID())
                .player1(playerName)
                .status(GameStatus.IN_PROGRESS)
                .build();
        repository.addGame(game);
        log.debug("Player {} created game {}", playerName, game.getId());

        return game.getId();
    }



    @Override
    public void joinGame(UUID gameId, String playerName) throws GameNotFoundException, JoinGameSamePlayerNameException, GameNotInProgressException, JoinFullGameException {
        Game game = repository.findGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId, "Game not found"));

        if (game.getPlayer1().equals(playerName)) {
            log.debug("Player {} already in game {}", playerName, gameId);
            throw new JoinGameSamePlayerNameException(gameId, "The game has already the player with this name" +playerName);
        }

        if (game.getPlayer2() != null) {
            log.debug("2 players are already in game {}", gameId);
            throw new JoinFullGameException(gameId, "The game have already all players set");
        }

        if (!game.getStatus().equals(GameStatus.IN_PROGRESS)) {
            log.debug("Game {} already finished", gameId);
            throw new GameNotInProgressException (gameId, "The game have already finished");
        }

        repository.addPlayerToGame(gameId, playerName);
        log.debug("Player {} joined game {}", playerName, gameId);
    }

    @Override
    public void makeMove(UUID gameId, String playerName, MoveEnum move)
            throws GameNotFoundException, GameNotInProgressException, PlayerAlreadyMadeMoveException {
        Game game = repository.findGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId, "Game not found"));

        if (!game.getStatus().equals(GameStatus.IN_PROGRESS)) {
            log.debug("Game {} not in progress", gameId);
            throw new GameNotInProgressException(gameId, "The game is not in progress");
        }

        try {
            if (game.getPlayer1().equals(playerName)) {
                repository.player1Move(gameId, move);
            }

            if (game.getPlayer2().equals(playerName)) {
                repository.player2Move(gameId, move);
            }
        } catch (IllegalStateException e) {
            throw new PlayerAlreadyMadeMoveException(gameId, "The player " + playerName + "  have already made a move");
        }

        if (game.getPlayer1Move() != null && game.getPlayer2Move() != null) {
            gameResolver.resolveGame(game);
        }
    }

    @Override
    public GameDto getGameResult(UUID gameId) throws GameNotFoundException {
        Game game = repository.findGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId, "Game not found"));

        return gameMapper.toGameDto(game);
    }
}
