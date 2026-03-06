package anagrafica.dto.company;

import java.util.List;

import lombok.Data;

@Data
public class CompanyInfoResponse {
    private List<CompanyStockResponse> stock;
    private List<CompanyMovementInner> movements;
    
}

