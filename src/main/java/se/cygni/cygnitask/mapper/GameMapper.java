package se.cygni.cygnitask.mapper;

import lombok.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.cygni.cygnitask.dto.GameDto;
import se.cygni.cygnitask.model.Game;
import se.cygni.cygnitask.rest.api.response.GameResultResponse;

/**
 * Map Game into DT in repository and DTO to response in controller.
 * Date: 08.07.2023
 *
 * @author Nikolay Zinin
 */
@Mapper
public interface GameMapper {
    GameDto toGameDto(Game game);

    @Mapping(source = "id", target = "gameId")
    GameResultResponse toGameResultResponse(GameDto gameDto);
}
