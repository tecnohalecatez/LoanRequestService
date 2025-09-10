package co.com.tecnohalecatez.model.loan.gateways;

import co.com.tecnohalecatez.model.loan.Loan;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LoanRepository {

    Mono<Loan> save(Loan loan);

    Flux<Loan> findByStateId(Integer stateId);

    Mono<Long> countByStateId(Integer stateId);

}
