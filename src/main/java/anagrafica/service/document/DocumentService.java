package anagrafica.service.document;

import anagrafica.dto.document.DocumentResponse;

public interface DocumentService {
	Long saveImage(final String base64);
	Long saveDocument(final String base64);
	DocumentResponse downloadDocument(final Long idDocument);
	void deleteDocument(final Long idDocument);
}
