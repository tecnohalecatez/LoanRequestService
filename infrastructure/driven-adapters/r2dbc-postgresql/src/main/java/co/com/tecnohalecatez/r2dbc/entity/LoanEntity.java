package co.com.tecnohalecatez.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigInteger;
import java.time.LocalDate;

@Table("loans")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class LoanEntity {
    @Id
    private BigInteger id;
    private Double amount;
    private Integer term;
    @Column("identity_document")
    private String identityDocument;
    private String email;
    @Column("registration_date")
    private LocalDate registrationDate;
    private Integer stateId;
    private Integer typeId;
}
