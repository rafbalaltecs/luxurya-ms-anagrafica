package anagrafica.service.document.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;

import anagrafica.service.document.StorageService;

@Service
public class StorageServiceImpl implements StorageService{
	private final GridFSBucket gridFSBucket;
	
	public StorageServiceImpl(GridFSBucket gridFSBucket) {
		this.gridFSBucket = gridFSBucket;
	}

	@Override
	public ObjectId saveFileFromBase64(String base64, String fileName, String contentType) {
		 String pureBase64 = base64.contains(",") 
		            ? base64.substring(base64.indexOf(',') + 1) 
		            : base64;
	     	
         byte[] bytes = Base64.getDecoder().decode(pureBase64);
         ByteArrayInputStream stream = new ByteArrayInputStream(bytes);

         GridFSUploadOptions options = new GridFSUploadOptions()
                 .chunkSizeBytes(358400)
                 .metadata(new org.bson.Document("contentType", contentType));

         return gridFSBucket.uploadFromStream(fileName, stream, options);
	}

	@Override
	public byte[] getFileAsStream(ObjectId fileId) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	    gridFSBucket.downloadToStream(fileId, out);
	    return out.toByteArray();
	}
}
