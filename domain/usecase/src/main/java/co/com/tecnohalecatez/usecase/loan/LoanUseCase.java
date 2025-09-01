package co.com.tecnohalecatez.usecase.loan;

import co.com.tecnohalecatez.model.loan.Loan;
import co.com.tecnohalecatez.model.loan.gateways.LoanRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RequiredArgsConstructor
public class LoanUseCase {

    private final LoanRepository loanRepository;

    public Mono<Loan> saveLoan(Loan loan) {
        loan.setStateId(1);
        loan.setRegistrationDate(LocalDate.now());
        return loanRepository.save(loan);
    }

}
