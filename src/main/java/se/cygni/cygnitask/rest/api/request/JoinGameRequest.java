package se.cygni.cygnitask.rest.api.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * A request with the second player name.
 * Date: 07.07.2023
 *
 * @author Nikolay Zinin
 */
@Getter
@Setter
public class JoinGameRequest {
    @NotEmpty
    private String name;
}
