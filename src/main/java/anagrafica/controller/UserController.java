package anagrafica.controller;

import anagrafica.dto.user.UserResponse;
import anagrafica.service.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Gestione Utenti")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<UserResponse>> findAll(
            @RequestParam("offset") Integer offset,
            @RequestParam("limit") Integer limit
    ){
        return ResponseEntity.ok(userService.findAll(offset, limit));
    }
}
