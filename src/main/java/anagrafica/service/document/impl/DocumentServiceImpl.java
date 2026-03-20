package anagrafica.service.document.impl;

import java.util.Optional;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import anagrafica.dto.document.DocumentResponse;
import anagrafica.entity.Document;
import anagrafica.exception.RestException;
import anagrafica.repository.voyage.DocumentRepository;
import anagrafica.service.document.DocumentService;
import anagrafica.service.document.StorageService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService{
	
	private final DocumentRepository documentRepository;
	private final StorageService storageService;
	
	public DocumentServiceImpl(final DocumentRepository documentRepository, final StorageService storageService) {
		this.documentRepository = documentRepository;
		this.storageService = storageService;
	}

	@Override
	public Long saveImage(String base64) {
		Document document = new Document();
		document.setType("IMAGE");
		final String hexaId = storageService.saveFileFromBase64(base64, UUID.randomUUID().toString(), "image/jpeg").toHexString();
		document.setHexaId(hexaId);
		document = documentRepository.save(document);
		return document.getId();
	}

	@Override
	public Long saveDocument(String base64) {
		Document document = new Document();
		document.setType("DOCUMENT");
		final String hexaId = storageService.saveFileFromBase64(
			    base64,
			    UUID.randomUUID().toString(),
			    "application/pdf"
			).toHexString();
		document.setHexaId(hexaId);
		document = documentRepository.save(document);
		return document.getId();
	}

	@Override
	public DocumentResponse downloadDocument(Long idDocument) {
		final Optional<Document> optionalDocument = documentRepository.findByIdNotDeleted(idDocument);
        if(!optionalDocument.isPresent()){
            throw new RestException("Document Not Found");
        }
        final DocumentResponse dto = new DocumentResponse();
        if(!ObjectId.isValid(optionalDocument.get().getHexaId())){
            throw new RestException("ObjectId Not Valid");
        }
        
        dto.setId(optionalDocument.get().getId());
        
        if(optionalDocument.get().getType().equals("IMAGE")) {
        	 dto.setNameFile(UUID.randomUUID().toString() + ".jpg");
        }else {
        	 dto.setNameFile(UUID.randomUUID().toString() + ".pdf");
        }
       
        dto.setContent(
            storageService.getFileAsStream(
                    new ObjectId(
                        optionalDocument.get().getHexaId()
                ))
        );

        return dto;
	}

	@Override
	@Transactional
	public void deleteDocument(Long idDocument) {
		final Optional<Document> optionalDocument = documentRepository.findByIdNotDeleted(idDocument);
        if(!optionalDocument.isPresent()){
            throw new RestException("Document Not Found");
        }
        optionalDocument.get().setDeleted(Boolean.TRUE);
        documentRepository.save(optionalDocument.get());
	}
}
