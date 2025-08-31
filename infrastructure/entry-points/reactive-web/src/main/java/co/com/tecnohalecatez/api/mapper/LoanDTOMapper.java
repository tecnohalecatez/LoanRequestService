package co.com.tecnohalecatez.api.mapper;

import co.com.tecnohalecatez.api.dto.LoanDTO;
import co.com.tecnohalecatez.api.dto.LoanDataDTO;
import co.com.tecnohalecatez.model.loan.Loan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanDTOMapper {

    LoanDTO toResponse(Loan loan);

    Loan toModel(LoanDataDTO loanDataDTO);
    
}
