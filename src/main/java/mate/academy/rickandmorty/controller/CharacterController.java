package mate.academy.rickandmorty.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Character management", description = "Endpoints for managing characters")
@RestController
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {
    private final CharacterService characterService;

    @Operation(
            summary = "Get a random Rick and Morty character",
            description = "Returns a randomly selected character from the database."
    )
    @ApiResponse(responseCode = "200", description = "Character returned successfully")
    @GetMapping("/random")
    public CharacterDto getRandomCharacter() {
        return characterService.getRandomCharacter();
    }

    @Operation(
            summary = "Search characters by name",
            description = "Returns list of characters whose names contain the search keyword."
    )
    @ApiResponse(responseCode = "200", description = "Characters found")
    @GetMapping("/search")
    public List<CharacterDto> getByName(
            @Parameter(
                    name = "name",
                    description = "Name or part of name to search for"
            ) @RequestParam String name) {
        return characterService.searchCharacters(name);
    }
}
