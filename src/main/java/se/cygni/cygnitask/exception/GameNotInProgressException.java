package se.cygni.cygnitask.exception;

import lombok.Getter;

import java.util.UUID;

/**
 * Date: 10.07.2023
 *
 * @author Nikolay Zinin
 */
@Getter
public class GameNotInProgressException extends GameException {
    public GameNotInProgressException(UUID gameId, String descriptionMessage) {
        super(gameId, descriptionMessage);
    }
}
