package se.cygni.cygnitask.rest.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

/**
 * A response in case of exception.
 * Date: 07.07.2023
 *
 * @author Nikolay Zinin
 */
@Getter
@Setter
@Builder

public class GameExceptionResponse {
    private int statusCode;
    private Date timestamp;
    private UUID gameUuid;
    private String path;
    private String descriptionMessage;
}
