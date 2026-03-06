package anagrafica.dto.ext;

import java.util.List;

import lombok.Data;

@Data
public class LoadVoyageProductResponseExt {
	private Long id;
	private Long voyageId;
	private List<LoadVoyageProductItemResponse> products;
}