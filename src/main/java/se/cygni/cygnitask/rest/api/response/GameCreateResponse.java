package se.cygni.cygnitask.rest.api.response;

import lombok.*;

import java.util.UUID;

/**
 * A response with the game id.
 * Date: 07.07.2023
 *
 * @author Nikolay Zinin
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class GameCreateResponse {
    private UUID gameId;
}
