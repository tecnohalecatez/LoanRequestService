package co.com.tecnohalecatez.r2dbc;

import co.com.tecnohalecatez.r2dbc.entity.LoanEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.math.BigInteger;


public interface LoanReactiveRepository extends ReactiveCrudRepository<LoanEntity, BigInteger>, ReactiveQueryByExampleExecutor<LoanEntity> {

}
