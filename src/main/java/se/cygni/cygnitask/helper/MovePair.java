package se.cygni.cygnitask.helper;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import se.cygni.cygnitask.rest.api.gameenum.MoveEnum;

/**
 * Move (ROCK, PAPER or SCISSORS) pair for two players moves int the game: player1: move1 and player2: move2.
 * Date: 07.07.2023
 *
 * @author Nikolay Zinin
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode

public class MovePair {
    private MoveEnum move1;
    private MoveEnum move2;
}
