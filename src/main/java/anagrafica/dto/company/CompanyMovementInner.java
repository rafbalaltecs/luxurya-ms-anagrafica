package anagrafica.dto.company;

import lombok.Data;

@Data
public class CompanyMovementInner {
   private Long operationId;
   private String operationName;
   private String typePaymentName;
   private String documentName;
   private String note;
   private String code;
}