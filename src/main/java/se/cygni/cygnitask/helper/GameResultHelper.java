package se.cygni.cygnitask.helper;

import org.springframework.stereotype.Component;
import se.cygni.cygnitask.rest.api.gameenum.MoveEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Help to define game result for all 6 different combinations ROCK, PAPER and SCISSORS of move pairs.
 * Date: 08.07.2023
 *
 * @author Nikolay Zinin
 */
@Component
public class GameResultHelper {
    private static final Map<MovePair, Boolean> PAIR_RESULT_MAP = new HashMap<>();

    static {
        PAIR_RESULT_MAP.put(new MovePair(MoveEnum.ROCK, MoveEnum.PAPER), false);
        PAIR_RESULT_MAP.put(new MovePair(MoveEnum.ROCK, MoveEnum.SCISSORS), true);
        PAIR_RESULT_MAP.put(new MovePair(MoveEnum.PAPER, MoveEnum.ROCK), true);
        PAIR_RESULT_MAP.put(new MovePair(MoveEnum.PAPER, MoveEnum.SCISSORS), false);
        PAIR_RESULT_MAP.put(new MovePair(MoveEnum.SCISSORS, MoveEnum.ROCK), false);
        PAIR_RESULT_MAP.put(new MovePair(MoveEnum.SCISSORS, MoveEnum.PAPER), true);
    }

    public boolean matchResult(MoveEnum player1Move, MoveEnum player2Move) {
        return PAIR_RESULT_MAP.get(new MovePair(player1Move, player2Move));
    }
}
