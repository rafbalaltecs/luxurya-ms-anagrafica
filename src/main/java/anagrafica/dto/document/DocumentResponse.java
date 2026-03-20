package anagrafica.dto.document;

import lombok.Data;

@Data
public class DocumentResponse {
	private Long id;
    private String nameFile;
    private byte[] content;
}
