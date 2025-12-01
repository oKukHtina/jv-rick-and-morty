package mate.academy.rickandmorty.dto.mapping;

import mate.academy.rickandmorty.dto.external.ExternalCharacterDto;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.entity.Character;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = mate.academy.rickandmorty.config.MapperConfig.class)
public interface CharacterMapper {
    CharacterDto toDto(Character character);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "id")
    Character toEntity(ExternalCharacterDto externalDto);
}
