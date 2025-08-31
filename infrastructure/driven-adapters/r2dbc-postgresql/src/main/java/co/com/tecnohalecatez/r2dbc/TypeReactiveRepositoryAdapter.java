package co.com.tecnohalecatez.r2dbc;

import co.com.tecnohalecatez.model.type.Type;
import co.com.tecnohalecatez.model.type.gateways.TypeRepository;
import co.com.tecnohalecatez.r2dbc.entity.TypeEntity;
import co.com.tecnohalecatez.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Slf4j
@Repository
public class TypeReactiveRepositoryAdapter extends ReactiveAdapterOperations<Type, TypeEntity,
        Integer, TypeReactiveRepository> implements TypeRepository {

    public TypeReactiveRepositoryAdapter(TypeReactiveRepository repository, ObjectMapper mapper) {

        super(repository, mapper, entity -> mapper.map(entity, Type.class));
    }

    @Override
    public Mono<Boolean> existsById(Integer id) {
        log.trace("Start checking existence of type by id = {}", id);
        return repository.existsById(id)
                .doOnSuccess(u -> log.trace("Type existence checked: {}", u))
                .doOnError(e -> log.error("Error checking type existence: {}", e.getMessage()));
    }
}
