package co.com.tecnohalecatez.api.config;

import co.com.tecnohalecatez.api.LoanHandler;
import co.com.tecnohalecatez.api.LoanRouterRest;
import co.com.tecnohalecatez.api.mapper.LoanDTOMapper;
import co.com.tecnohalecatez.model.loan.Loan;
import co.com.tecnohalecatez.usecase.loan.LoanUseCase;
import co.com.tecnohalecatez.usecase.type.TypeUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {LoanRouterRest.class, LoanHandler.class, LoanPath.class})
@WebFluxTest
@Import({CorsConfig.class, SecurityHeadersConfig.class})
class ConfigTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private LoanPath loanPath;

    @MockitoBean
    private LoanUseCase loanUseCase;

    @MockitoBean
    private TypeUseCase typeUseCase;

    @MockitoBean
    private LoanDTOMapper loanDTOMapper;

    private final Loan testLoanOne = Loan.builder()
            .id(BigInteger.ONE)
            .amount(1000.0)
            .term(12)
            .identityDocument("1234567890")
            .email("ejemplo@prueba.com")
            .registrationDate(LocalDate.now())
            .stateId(1)
            .typeId(1)
            .build();

    private final Loan testLoanTwo = Loan.builder()
            .id(BigInteger.ONE)
            .amount(1000.0)
            .term(12)
            .identityDocument("1234567890")
            .email("ejemplo@prueba.com")
            .registrationDate(LocalDate.now())
            .stateId(1)
            .typeId(1)
            .build();

    @BeforeEach
    void setUp() {
        when(loanUseCase.saveLoan(testLoanOne)).thenReturn(Mono.just(testLoanTwo));
    }

    @Test
    void shouldLoadUserPathProperties() {
        assertEquals("/api/v1/loans", loanPath.getLoans());
        assertEquals("/api/v1/loans/{id}", loanPath.getLoansById());
    }

    @Test
    void corsConfigurationShouldAllowOrigins() {
        webTestClient.get()
                .uri("/api/v1/u")
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().valueEquals("Content-Security-Policy",
                        "default-src 'self'; frame-ancestors 'self'; form-action 'self'")
                .expectHeader().valueEquals("Strict-Transport-Security", "max-age=31536000;")
                .expectHeader().valueEquals("X-Content-Type-Options", "nosniff")
                .expectHeader().valueEquals("Server", "")
                .expectHeader().valueEquals("Cache-Control", "no-store")
                .expectHeader().valueEquals("Pragma", "no-cache")
                .expectHeader().valueEquals("Referrer-Policy", "strict-origin-when-cross-origin");
    }

}