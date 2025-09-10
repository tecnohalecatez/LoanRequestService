package co.com.tecnohalecatez.api;

import co.com.tecnohalecatez.api.config.LoanPath;
import co.com.tecnohalecatez.api.dto.LoanDTO;
import co.com.tecnohalecatez.api.dto.LoanDataDTO;
import co.com.tecnohalecatez.api.dto.PageResponseDTO;
import co.com.tecnohalecatez.api.exception.GlobalExceptionHandler;
import co.com.tecnohalecatez.api.mapper.LoanDTOMapper;
import co.com.tecnohalecatez.model.loan.Loan;
import co.com.tecnohalecatez.usecase.loan.LoanUseCase;
import co.com.tecnohalecatez.usecase.type.TypeUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.validation.Validator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.math.BigInteger;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {LoanRouterRest.class, LoanHandler.class, GlobalExceptionHandler.class})
@EnableConfigurationProperties(LoanPath.class)
@WebFluxTest(excludeAutoConfiguration = {
        ReactiveSecurityAutoConfiguration.class,
        ReactiveUserDetailsServiceAutoConfiguration.class
})
class LoanRouterRestTest {

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

    @MockitoBean
    private Validator validator;

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

    private final LoanDTO testLoanDTO = new LoanDTO(
            BigInteger.ONE,
            1000.0,
            12,
            "1234567890",
            "test@test.com",
            LocalDate.now(),
            1,
            1);

    private final String loans = "/api/v1/loans";

    @Test
    void shouldLoadUserPathProperties() {
        assertEquals(loans, loanPath.getLoans());
        assertEquals(loans + "/{id}", loanPath.getLoansById());
    }

    @Test
    void listenSaveLoanReturnsCreated() {
        when(typeUseCase.existsById(1)).thenReturn(Mono.just(true));
        when(loanDTOMapper.toModel(testLoanDataDTO)).thenReturn(testLoan);
        when(loanUseCase.saveLoan(any(Loan.class))).thenReturn(Mono.just(testLoan));
        when(loanDTOMapper.toResponse(testLoan)).thenReturn(testLoanDTO);
        webTestClient.post()
                .uri(loans)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(testLoanDataDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(LoanDTO.class);
    }

    @Test
    void listenSaveLoanReturnsBadRequestWhenTypeNotExists() {
        when(typeUseCase.existsById(1)).thenReturn(Mono.just(false));
        webTestClient.post()
                .uri(loans)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(testLoanDataDTO)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void listenSaveLoanReturnsBadRequestWithInvalidData() {
        LoanDataDTO invalidLoanData = new LoanDataDTO(
                -1000.0,
                0,
                "",
                "invalid-email",
                1);
        webTestClient.post()
                .uri(loans)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidLoanData)
                .exchange()
                .expectStatus().isBadRequest();
    }



    @Test
    void listenGetLoansPageReturnsOk() {
        when(loanUseCase.countLoansByStateId(1)).thenReturn(Mono.just(1L));
        when(loanUseCase.getLoansByStateIdPaginated(1, 0, 10))
                .thenReturn(Flux.just(testLoan));
        when(loanDTOMapper.toResponse(testLoan)).thenReturn(testLoanDTO);
        webTestClient.get()
                .uri(loans + "?page=0&size=10")
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageResponseDTO.class);
    }

    @Test
    void listenGetLoansPageWithDefaultParams() {
        when(loanUseCase.countLoansByStateId(1)).thenReturn(Mono.just(1L));
        when(loanUseCase.getLoansByStateIdPaginated(1, 0, 10))
                .thenReturn(Flux.just(testLoan));
        when(loanDTOMapper.toResponse(testLoan)).thenReturn(testLoanDTO);
        webTestClient.get()
                .uri(loans)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void listenGetLoansPageReturnsNotFoundWhenEmpty() {
        when(loanUseCase.countLoansByStateId(1)).thenReturn(Mono.just(0L));
        webTestClient.get()
                .uri(loans)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void listenGetLoansPageWithCustomPagination() {
        when(loanUseCase.countLoansByStateId(1)).thenReturn(Mono.just(25L));
        when(loanUseCase.getLoansByStateIdPaginated(1, 2, 5))
                .thenReturn(Flux.just(testLoan));
        when(loanDTOMapper.toResponse(testLoan)).thenReturn(testLoanDTO);
        webTestClient.get()
                .uri(loans + "?page=2&size=5")
                .exchange()
                .expectStatus().isOk();
    }

}
