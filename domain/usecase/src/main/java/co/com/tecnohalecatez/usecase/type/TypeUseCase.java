package co.com.tecnohalecatez.usecase.type;

import co.com.tecnohalecatez.model.type.gateways.TypeRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class TypeUseCase {

    private final TypeRepository typeRepository;

    public Mono<Boolean> existsById(Integer id) {
        return typeRepository.existsById(id);
    }

}
