package co.com.tecnohalecatez.r2dbc;

import co.com.tecnohalecatez.model.loan.Loan;
import co.com.tecnohalecatez.r2dbc.entity.LoanEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigInteger;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanReactiveRepositoryAdapterTest {

    @InjectMocks
    LoanReactiveRepositoryAdapter repositoryAdapter;

    @Mock
    LoanReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

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

    private final LoanEntity testLoanEntity = LoanEntity.builder()
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
    void saveShouldReturnSavedLoan() {
        when(mapper.map(testLoan, LoanEntity.class)).thenReturn(testLoanEntity);
        when(repository.save(any(LoanEntity.class))).thenReturn(Mono.just(testLoanEntity));
        when(mapper.map(testLoanEntity, Loan.class)).thenReturn(testLoan);

        Mono<Loan> result = repositoryAdapter.save(testLoan);

        StepVerifier.create(result)
                .expectNext(testLoan)
                .verifyComplete();
    }
}
