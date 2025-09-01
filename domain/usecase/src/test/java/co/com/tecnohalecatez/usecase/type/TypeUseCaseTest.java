package co.com.tecnohalecatez.usecase.type;

import co.com.tecnohalecatez.model.type.gateways.TypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TypeUseCaseTest {

    @Mock
    private TypeRepository typeRepository;

    @InjectMocks
    private TypeUseCase typeUseCase;

    @Test
    void existsByIdShouldReturnTrue() {
        when(typeRepository.existsById(1)).thenReturn(Mono.just(true));

        Mono<Boolean> result = typeUseCase.existsById(1);

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();
    }

}
