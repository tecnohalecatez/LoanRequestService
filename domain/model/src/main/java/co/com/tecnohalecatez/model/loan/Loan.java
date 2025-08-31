package co.com.tecnohalecatez.model.loan;

import lombok.*;

import java.math.BigInteger;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class Loan {
    private BigInteger id;
    private Double amount;
    private Integer term;
    private String identityDocument;
    private String email;
    private LocalDate registrationDate;
    private Integer stateId;
    private Integer typeId;

}
