package co.com.tecnohalecatez.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("types")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class TypeEntity {
    @Id
    private Integer id;
    private String name;
    @Column("mim_amount")
    private Double minAmount;
    @Column("max_amount")
    private Double maxAmount;
    @Column("interest_rate")
    private Double interestRate;
    @Column("automatic_validation")
    private Boolean automaticValidation;

}
