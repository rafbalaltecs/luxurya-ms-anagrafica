package anagrafica.entity.audit;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document("external_system")
@Data
public class ExternalSystemAudit {
	@Id
    private String id;
	private Long idUser;
	private Long idExternalSystem;
	private LocalDateTime requestDate;
	private LocalDateTime responseDate;
	private String request;
	private String response;
	private String method;
	private String path;

}
