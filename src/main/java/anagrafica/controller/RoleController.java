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

import anagrafica.dto.user.TypeUserRequest;
import anagrafica.dto.user.TypeUserResponse;
import anagrafica.service.user.TypeUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/role")
@Tag(name = "Gestione Ruoli")
@Slf4j
public class RoleController {

	private final TypeUserService typeUserService;
	
	public RoleController(TypeUserService typeUserService) {
		this.typeUserService = typeUserService;
	}
	
	@PostMapping(value = "", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<TypeUserResponse> create(@RequestBody final TypeUserRequest request){
    	return ResponseEntity.ok(typeUserService.create(request));
    }

    @PutMapping(value = "/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<TypeUserResponse> update(
    		@PathVariable("id") Long id,
    		@RequestBody final TypeUserRequest request){
    	return ResponseEntity.ok(typeUserService.update(id, request));
    }
    
    @DeleteMapping(value = "/{id}", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void delete(
    		@PathVariable("id") Long id){
    	typeUserService.delete(id);
    }
    
    @GetMapping(value = "", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<TypeUserResponse>> findAll(
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit
    ){
        return ResponseEntity.ok(typeUserService.findAll(offset, limit));
    }
	
}
