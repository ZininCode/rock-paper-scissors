package se.cygni.cygnitask.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import se.cygni.cygnitask.rest.api.gameenum.GameStatus;


import java.util.UUID;
/**
 * DTO from Game mapped to get data and transfer to controller.
 * Date: 08.07.2023
 *
 * @author Nikolay Zinin
 */

@Getter
@Setter
@Builder
public class GameDto {
    private UUID id;
    private GameStatus status;
    private String winner;
}
