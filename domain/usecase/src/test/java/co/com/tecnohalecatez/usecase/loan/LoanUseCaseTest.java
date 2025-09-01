package co.com.tecnohalecatez.usecase.loan;

import co.com.tecnohalecatez.model.loan.Loan;
import co.com.tecnohalecatez.model.loan.gateways.LoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigInteger;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanUseCaseTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanUseCase loanUseCase;

    private final Loan testLoan = Loan.builder()
            .id(BigInteger.ONE)
            .amount(1000.0)
            .term(12)
            .identityDocument("1234567890")
            .email("ejemplo@prueba.com")
            .registrationDate(LocalDate.now())
            .stateId(1)
            .typeId(1)
            .build();

    @Test
    void saveLoanShouldReturnSavedLoan() {
        when(loanRepository.save(any(Loan.class))).thenReturn(Mono.just(testLoan));

        Mono<Loan> result = loanUseCase.saveLoan(testLoan);

        StepVerifier.create(result)
                .expectNext(testLoan)
                .verifyComplete();
    }
}
