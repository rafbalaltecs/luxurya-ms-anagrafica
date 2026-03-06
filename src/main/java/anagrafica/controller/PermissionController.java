package anagrafica.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import anagrafica.dto.role.PermissionRequest;
import anagrafica.dto.role.PermissionResponse;
import anagrafica.service.user.PermissionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/permission")
@Tag(name = "Gestione Permessi")
@Slf4j
public class PermissionController {
	
	private final PermissionService permissionService;
	
	public PermissionController(PermissionService permissionService) {
		this.permissionService = permissionService;
	}
	
	@PostMapping(value = "", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PermissionResponse> create(@RequestBody final PermissionRequest request){
    	return ResponseEntity.ok(permissionService.create(request));
    }

    @PutMapping(value = "/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PermissionResponse> update(
    		@PathVariable("id") Long id,
    		@RequestBody final PermissionRequest request){
    	return ResponseEntity.ok(permissionService.update(id, request));
    }
    
    @DeleteMapping(value = "/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void delete(
    		@PathVariable("id") Long id){
    	permissionService.delete(id);
    }
    
    @GetMapping(value = "", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<PermissionResponse>> findAll(
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit
    ){
        return ResponseEntity.ok(permissionService.findAll(offset, limit));
    }

}
