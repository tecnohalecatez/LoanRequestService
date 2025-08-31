package co.com.tecnohalecatez.usecase.loan;

import co.com.tecnohalecatez.model.loan.Loan;
import co.com.tecnohalecatez.model.loan.gateways.LoanRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoanUseCase {

    private final LoanRepository loanRepository;

    public Mono<Loan> save(Loan loan) {
        return loanRepository.save(loan);
    }

}
