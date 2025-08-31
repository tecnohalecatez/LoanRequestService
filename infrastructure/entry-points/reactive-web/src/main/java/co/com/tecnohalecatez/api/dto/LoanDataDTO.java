package co.com.tecnohalecatez.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LoanDataDTO(
        @NotNull
        @Positive
        Double amount,
        @NotNull
        @Positive
        Integer term,
        @NotBlank
        String identityDocument,
        @NotBlank
        @Email
        String email,
        @NotNull
        Integer typeId) {
}
