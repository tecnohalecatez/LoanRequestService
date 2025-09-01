package co.com.tecnohalecatez.model.type.gateways;

import reactor.core.publisher.Mono;

public interface TypeRepository {

    Mono<Boolean> existsById(Integer id);
}
