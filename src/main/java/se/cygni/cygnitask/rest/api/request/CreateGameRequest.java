package se.cygni.cygnitask.rest.api.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * A request containing the first player name.
 * Date: 07.07.2023
 *
 * @author Nikolay Zinin
 */
@Getter
@Setter

public class CreateGameRequest {
    @NotEmpty
    private String name;
}
