package co.com.tecnohalecatez.api;

import co.com.tecnohalecatez.api.config.LoanPath;
import co.com.tecnohalecatez.api.constant.LoanConstant;
import co.com.tecnohalecatez.api.dto.ErrorResponseDTO;
import co.com.tecnohalecatez.api.dto.LoanDTO;
import co.com.tecnohalecatez.api.dto.LoanDataDTO;
import co.com.tecnohalecatez.api.exception.GlobalExceptionHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class LoanRouterRest {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = LoanConstant.BASE_PATH,
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.POST,
                    beanClass = LoanHandler.class,
                    beanMethod = LoanConstant.LISTEN + LoanConstant.SAVE_LOAN,
                    operation = @Operation(
                            operationId = LoanConstant.SAVE_LOAN,
                            summary = "🆕  Register a new loan",
                            description = "Receives a LoanDataDTO object and saves a new loan in the system.",
                            tags = {"Loans"},
                            requestBody = @RequestBody(
                                    required = true,
                                    description = "Loan creation data",
                                    content = @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = LoanDataDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Loan successfully registered",
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = LoanDTO.class)
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = LoanConstant.INVALID_LOAN_DATA,
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                                            )
                                    ),
                                    @ApiResponse(
                                            responseCode = "500",
                                            description = LoanConstant.INTERNAL_SERVER_ERROR,
                                            content = @Content(
                                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                                            )
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(LoanHandler loanHandler, LoanPath loadPath, GlobalExceptionHandler globalExceptionHandler) {
        return route(POST(loadPath.getLoans()), loanHandler::listenSaveLoan)
                .andRoute(GET(loadPath.getLoans()), loanHandler::listenGetLoansPage);
    }
}
