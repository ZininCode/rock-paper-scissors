package se.cygni.cygnitask.rest.api.gameenum;

/**
 * A game as created will get status "IN_PROGRESS" and when finished the status will change to a "DRAW" or "HAS_WINNER"
 * Date: 07.07.2023
 *
 * @author Nikolay Zinin
 */
public enum GameStatus {
    IN_PROGRESS,
    HAS_WINNER,
    DRAW
}
