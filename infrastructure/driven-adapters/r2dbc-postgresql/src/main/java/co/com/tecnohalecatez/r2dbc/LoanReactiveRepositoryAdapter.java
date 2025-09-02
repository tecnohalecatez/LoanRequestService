package co.com.tecnohalecatez.r2dbc;

import co.com.tecnohalecatez.model.loan.Loan;
import co.com.tecnohalecatez.model.loan.gateways.LoanRepository;
import co.com.tecnohalecatez.r2dbc.entity.LoanEntity;
import co.com.tecnohalecatez.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Slf4j
@Repository
public class LoanReactiveRepositoryAdapter extends ReactiveAdapterOperations<Loan, LoanEntity,
        BigInteger, LoanReactiveRepository> implements LoanRepository {

    public LoanReactiveRepositoryAdapter(LoanReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, entity -> mapper.map(entity, Loan.class));
    }

    @Override
    @Transactional
    public Mono<Loan> save(Loan loan) {
        log.trace("Start save loan: {}", loan.toString());
        return super.save(loan)
                .doOnSuccess(u -> log.trace("Loan saved: {}", u.toString()))
                .doOnError(e -> log.error("Error saving loan: {}", e.getMessage()));
    }

}
