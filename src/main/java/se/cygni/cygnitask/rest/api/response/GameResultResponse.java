package se.cygni.cygnitask.rest.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import se.cygni.cygnitask.rest.api.gameenum.GameStatus;

import java.util.UUID;

/**
 * A response for the game result and status.
 * Date: 08.07.2023
 *
 * @author Nikolay Zinin
 */
@Getter
@Setter
@Builder

public class GameResultResponse {
    private UUID gameId;
    private GameStatus status;
    private String winner;
}
