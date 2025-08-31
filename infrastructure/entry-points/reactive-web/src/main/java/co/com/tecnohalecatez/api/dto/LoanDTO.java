package co.com.tecnohalecatez.api.dto;

import java.math.BigInteger;
import java.time.LocalDate;

public record LoanDTO(
        BigInteger id,
        Double amount,
        Integer term,
        String identityDocument,
        String email,
        LocalDate registrationDate,
        Integer stateId,
        Integer typeId) {
}
