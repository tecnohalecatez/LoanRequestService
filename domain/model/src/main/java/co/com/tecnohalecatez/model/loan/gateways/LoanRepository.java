package co.com.tecnohalecatez.model.loan.gateways;

import co.com.tecnohalecatez.model.loan.Loan;
import reactor.core.publisher.Mono;

public interface LoanRepository {

    Mono<Loan> save(Loan loan);

}
