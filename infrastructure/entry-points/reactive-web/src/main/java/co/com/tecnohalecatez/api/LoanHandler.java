package co.com.tecnohalecatez.api;

import co.com.tecnohalecatez.api.constant.LoanConstant;
import co.com.tecnohalecatez.api.dto.LoanDTO;
import co.com.tecnohalecatez.api.dto.LoanDataDTO;
import co.com.tecnohalecatez.api.dto.ErrorResponseDTO;
import co.com.tecnohalecatez.api.exception.LoanDataException;
import co.com.tecnohalecatez.api.mapper.LoanDTOMapper;
import co.com.tecnohalecatez.usecase.loan.LoanUseCase;
import co.com.tecnohalecatez.usecase.type.TypeUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoanHandler {

    private final LoanUseCase loanUseCase;
    private final TypeUseCase typeUseCase;
    private final LoanDTOMapper loanDTOMapper;
    private final Validator validator;

    public Mono<ServerResponse> listenSaveLoan(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(LoanDataDTO.class)
                .flatMap(loanDataDTO -> {
                    BeanPropertyBindingResult errors = new BeanPropertyBindingResult(loanDataDTO, LoanDTO.class.getName());
                    validator.validate(loanDataDTO, errors);
                    if (errors.hasErrors()) {
                        return Mono.error(new LoanDataException(LoanConstant.INVALID_LOAN_DATA));
                    }
                    return typeUseCase.existsById(loanDataDTO.typeId())
                            .flatMap(exists -> {
                                if (Boolean.FALSE.equals(exists)) {
                                    return Mono.error(new LoanDataException(LoanConstant.INVALID_LOAN_DATA));
                                }
                                return loanUseCase.saveLoan(loanDTOMapper.toModel(loanDataDTO));
                            });
                })
                .flatMap(savedLoan -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(loanDTOMapper.toResponse(savedLoan)))
                .onErrorResume(LoanDataException.class, e -> {
                    ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                            Instant.now().toString(),
                            400,
                            "Bad Request",
                            e.getMessage()
                    );
                    return ServerResponse.badRequest()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(errorResponse);
                })
                .onErrorResume(e -> {
                    ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                            Instant.now().toString(),
                            500,
                            "Internal Server",
                            e.getMessage()
                    );
                    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(errorResponse);
                });
    }
}
