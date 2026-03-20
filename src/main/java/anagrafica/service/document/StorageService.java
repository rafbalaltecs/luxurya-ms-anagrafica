package anagrafica.service.document;

import org.bson.types.ObjectId;

public interface StorageService {

	ObjectId saveFileFromBase64(String base64, String fileName, String contentType);
	byte[] getFileAsStream(ObjectId fileId);
}
