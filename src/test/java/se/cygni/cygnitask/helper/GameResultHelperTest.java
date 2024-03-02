package se.cygni.cygnitask.helper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import se.cygni.cygnitask.rest.api.gameenum.MoveEnum;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Date: 15.07.2023
 *
 * @author Nikolay Zinin
 */
class GameResultHelperTest {

    GameResultHelper gameResultHelper;

    @BeforeEach
    public void initService() {
        gameResultHelper = new GameResultHelper();
    }

    @Test
    public void testRockWinScissors() {
        boolean result = gameResultHelper.matchResult(MoveEnum.ROCK, MoveEnum.SCISSORS);
        assertEquals(true, result);
    }

    @Test
    public void testScissorsLoseRock() {
        boolean result = gameResultHelper.matchResult(MoveEnum.SCISSORS, MoveEnum.ROCK);
        assertEquals(false, result);
    }

    @Test
    public void testScissorsWinPaper() {
        boolean result = gameResultHelper.matchResult(MoveEnum.SCISSORS, MoveEnum.PAPER);
        assertEquals(true, result);
    }

    @Test
    public void testPaperLoseScissors() {
        boolean result = gameResultHelper.matchResult(MoveEnum.PAPER, MoveEnum.SCISSORS);
        assertEquals(false, result);
    }
}
