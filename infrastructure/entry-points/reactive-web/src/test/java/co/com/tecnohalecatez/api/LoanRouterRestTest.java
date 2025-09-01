package co.com.tecnohalecatez.api;

import co.com.tecnohalecatez.api.config.LoanPath;
import co.com.tecnohalecatez.api.dto.LoanDataDTO;
import co.com.tecnohalecatez.api.mapper.LoanDTOMapper;
import co.com.tecnohalecatez.model.loan.Loan;
import co.com.tecnohalecatez.usecase.loan.LoanUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {LoanRouterRest.class, LoanHandler.class})
@EnableConfigurationProperties(LoanPath.class)
@WebFluxTest
class LoanRouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private LoanPath loanPath;

    @MockitoBean
    private LoanUseCase loanUseCase;

    @MockitoBean
    private LoanHandler loanHandler;

    @MockitoBean
    private LoanDTOMapper loanDTOMapper;

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

    private final LoanDataDTO testLoanDataDTO = new LoanDataDTO(
            1000.0,
            12,
            "1234567890",
            "ejemplo@prueba.com",
            1);

    private final String loans = "/api/v1/loans";

    @Test
    void shouldLoadUserPathProperties() {
        assertEquals(loans, loanPath.getLoans());
        assertEquals("/api/v1/loans/{id}", loanPath.getLoansById());
    }

    @Test
    void listenSaveLoanReturnsCreated() {
        when(loanUseCase.saveLoan(any(Loan.class))).thenReturn(Mono.just(testLoan));
        Mockito.when(loanHandler.listenSaveLoan(Mockito.any()))
                .thenReturn(Mono.just(ServerResponse.created(null).build().block()));
        webTestClient.post()
                .uri(loans)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(testLoanDataDTO)
                .exchange()
                .expectStatus().isCreated();
    }

}
