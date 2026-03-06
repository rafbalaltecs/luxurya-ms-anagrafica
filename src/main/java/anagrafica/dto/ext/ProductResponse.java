package anagrafica.dto.ext;

import lombok.Data;

@Data
public class ProductResponse {
	  private Long id;
	    private String code;
	    private String name;
	    private String description;
	    private String sku;
	    private Integer quantity;
	    private Long catalogId;
	    private Integer vat;
	    private Double price;
	    private Long typeProductId;
	    private Long typeFormatId;
}
