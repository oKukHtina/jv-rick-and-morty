package mate.academy.rickandmorty.dto.external;

import java.util.List;
import lombok.Data;

@Data
public class ExternalResponseDto {
    private List<ExternalCharacterDto> results;
}
