package se.cygni.cygnitask.rest.api.request;

import lombok.Getter;
import lombok.Setter;
import se.cygni.cygnitask.rest.api.gameenum.MoveEnum;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * A request with a player name amd a move.
 * Date: 07.07.2023
 *
 * @author Nikolay Zinin
 */
@Getter
@Setter

public class MoveRequest {
    @NotEmpty
    private String name;
    @NotNull
    private MoveEnum move;
}
