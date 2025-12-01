package mate.academy.rickandmorty.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.external.ExternalResponseDto;
import mate.academy.rickandmorty.dto.mapping.CharacterMapper;
import mate.academy.rickandmorty.entity.Character;
import mate.academy.rickandmorty.exception.HttpSendRequestException;
import mate.academy.rickandmorty.repository.CharacterRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CharactersClient {
    private static final String BASE_URL = "https://rickandmortyapi.com/api/character";
    private final ObjectMapper objectMapper;
    private final CharacterMapper characterMapper;
    private final CharacterRepository characterRepository;

    public List<Character> downloadCharacters() {
        HttpClient httpClient = HttpClient.newHttpClient();
        String nextPage = BASE_URL;
        List<Character> allCharacters = new ArrayList<>();

        while (nextPage != null) {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(nextPage))
                    .build();
            try {
                HttpResponse<String> response = httpClient.send(
                        httpRequest,
                        HttpResponse.BodyHandlers.ofString());

                ExternalResponseDto responseDto = objectMapper.readValue(
                        response.body(), ExternalResponseDto.class);

                List<Character> pageCharacters = responseDto.getResults().stream()
                        .map(characterMapper::toEntity)
                        .toList();
                allCharacters.addAll(pageCharacters);

                nextPage = responseDto.getInfo().next();
            } catch (IOException | InterruptedException e) {
                throw new HttpSendRequestException("Cant send request to RickAndMorty API");
            }
        }

        return characterRepository.saveAll(allCharacters);
    }

    @PostConstruct
    public void init() {
        if (characterRepository.count() == 0) {
            downloadCharacters();
        }
    }
}
