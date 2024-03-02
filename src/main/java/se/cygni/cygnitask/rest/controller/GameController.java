package se.cygni.cygnitask.rest.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import se.cygni.cygnitask.dto.GameDto;
import se.cygni.cygnitask.exception.*;
import se.cygni.cygnitask.mapper.GameMapper;
import se.cygni.cygnitask.rest.api.request.CreateGameRequest;
import se.cygni.cygnitask.rest.api.request.JoinGameRequest;
import se.cygni.cygnitask.rest.api.request.MoveRequest;
import se.cygni.cygnitask.rest.api.response.GameCreateResponse;
import se.cygni.cygnitask.rest.api.response.GameResultResponse;
import se.cygni.cygnitask.service.GameService;

import javax.validation.Valid;
import java.util.UUID;

/**
 * Game controller.
 * Date: 07.07.2023
 *
 * @author Nikolay Zinin
 */
@RestController
@RequestMapping(path = "/api/games", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j

public class GameController {
    private final GameService gameService;
    private final GameMapper gameMapper;
    
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public GameCreateResponse create(@RequestBody @Valid CreateGameRequest request) {
        UUID gameId = gameService.createGame(request.getName());
        return GameCreateResponse.builder().gameId(gameId).build();
    }

    @PostMapping(path = "/{gameId}/join", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void join(@PathVariable("gameId") UUID gameId, @RequestBody @Valid JoinGameRequest request) throws JoinGameSamePlayerNameException, GameNotFoundException, JoinFullGameException, GameNotInProgressException {
        gameService.joinGame(gameId, request.getName());
    }

    @PostMapping(path = "/{gameId}/move", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void makeMove(@PathVariable("gameId") UUID gameId, @RequestBody @Valid MoveRequest request) throws GameNotInProgressException, GameNotFoundException, PlayerAlreadyMadeMoveException {
        gameService.makeMove(gameId, request.getName(), request.getMove());
    }
    
    @GetMapping(path = "/{gameId}")
    public GameResultResponse getGameResult(@PathVariable("gameId") UUID gameId) throws GameNotFoundException {
        GameDto gameDto = gameService.getGameResult(gameId);

        return gameMapper.toGameResultResponse(gameDto);
    }
}
