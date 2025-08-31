package co.com.tecnohalecatez.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class LoanRouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(LoanHandler loanHandler) {
        return route(GET("/api/usecase/path"), loanHandler::listenGETUseCase)
                .andRoute(POST("/api/usecase/otherpath"), loanHandler::listenPOSTUseCase)
                .and(route(GET("/api/otherusercase/path"), loanHandler::listenGETOtherUseCase));
    }
}
