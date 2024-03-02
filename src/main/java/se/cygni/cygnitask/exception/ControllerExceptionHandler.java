package se.cygni.cygnitask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import se.cygni.cygnitask.rest.api.response.GameExceptionResponse;
import se.cygni.cygnitask.rest.api.response.GameExceptionResponse;
import java.util.Date;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
/**
 * Handle exception to give a response which will be presented in JSON when exception is thrown.
 * Date: 10.07.2023
 *
 * @author Nikolay Zinin
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<GameExceptionResponse> gameNotFoundException(GameNotFoundException ex, WebRequest request) {
        GameExceptionResponse response = GameExceptionResponse
                .builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(new Date())
                .gameUuid(ex.getGameId())
                .path(request.getDescription(false))
                .descriptionMessage(ex.getDescriptionMessage())
                .build();
        return new ResponseEntity<>(response, NOT_FOUND);
    }

    @ExceptionHandler(JoinFullGameException.class)
    public ResponseEntity<GameExceptionResponse> joinFullGameException(JoinFullGameException ex, WebRequest request) {
        GameExceptionResponse response = GameExceptionResponse
                .builder()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .timestamp(new Date())
                .gameUuid(ex.getGameId())
                .path(request.getDescription(false))
                .descriptionMessage(ex.getDescriptionMessage())
                .build();

        return new ResponseEntity<>(response, FORBIDDEN);
    }

    @ExceptionHandler(GameNotInProgressException.class)
    public ResponseEntity<GameExceptionResponse> gameNotInProgressException(GameNotInProgressException ex, WebRequest request) {
        GameExceptionResponse response = GameExceptionResponse
                .builder()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .timestamp(new Date())
                .gameUuid(ex.getGameId())
                .path(request.getDescription(false))
                .descriptionMessage(ex.getDescriptionMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(JoinGameSamePlayerNameException.class)
    public ResponseEntity<GameExceptionResponse> joinGameException(JoinGameSamePlayerNameException ex, WebRequest request) {
        GameExceptionResponse response = GameExceptionResponse
                .builder()
                .statusCode(FORBIDDEN.value())
                .timestamp(new Date())
                .gameUuid(ex.getGameId())
                .path(request.getDescription(false))
                .descriptionMessage(ex.getDescriptionMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(PlayerAlreadyMadeMoveException.class)
    public ResponseEntity<GameExceptionResponse> playerAlreadyMakeMoveException(PlayerAlreadyMadeMoveException ex, WebRequest request) {
        GameExceptionResponse response = GameExceptionResponse
                .builder()
                .statusCode(FORBIDDEN.value())
                .timestamp(new Date())
                .gameUuid(ex.getGameId())
                .path(request.getDescription(false))
                .descriptionMessage(ex.getDescriptionMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

}
