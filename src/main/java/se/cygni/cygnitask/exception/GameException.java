package se.cygni.cygnitask.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

/**
 * Date: 10.07.2023
 *
 * @author Nikolay Zinin
 */
@Getter
@AllArgsConstructor
public class GameException extends Exception {
    private final UUID gameId;
    private final String descriptionMessage;
}
