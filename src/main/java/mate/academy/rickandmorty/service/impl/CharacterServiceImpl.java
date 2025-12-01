package mate.academy.rickandmorty.service.impl;

import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.dto.mapping.CharacterMapper;
import mate.academy.rickandmorty.entity.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {
    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;
    private final Random random = new Random();

    @Override
    public CharacterDto getRandomCharacter() {
        long count = characterRepository.count();

        if (count == 0) {
            throw new RuntimeException("No characters in database");
        }

        int randomIndex = random.nextInt((int) count);

        Character randomCharacter = characterRepository
                .findAll(PageRequest.of(randomIndex, 1))
                .getContent()
                .get(0);

        return characterMapper.toDto(randomCharacter);
    }

    @Override
    public List<CharacterDto> searchCharacters(String name) {
        return characterRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(characterMapper::toDto)
                .toList();
    }
}
