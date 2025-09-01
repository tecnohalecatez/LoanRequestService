package co.com.tecnohalecatez.r2dbc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TypeReactiveRepositoryAdapterTest {

    @InjectMocks
    TypeReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    TypeReactiveRepository repository;

    @Test
    void existsByIdShouldReturnTrue() {
        when(repository.existsById(1)).thenReturn(Mono.just(true));

        Mono<Boolean> result = repositoryAdapter.existsById(1);

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();
    }

}
