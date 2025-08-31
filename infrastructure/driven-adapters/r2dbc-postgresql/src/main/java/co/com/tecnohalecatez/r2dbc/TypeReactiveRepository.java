package co.com.tecnohalecatez.r2dbc;

import co.com.tecnohalecatez.r2dbc.entity.TypeEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TypeReactiveRepository extends ReactiveCrudRepository<TypeEntity, Integer>, ReactiveQueryByExampleExecutor<TypeEntity> {

}
