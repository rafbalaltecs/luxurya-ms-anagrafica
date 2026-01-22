package anagrafica.controller;

import anagrafica.dto.auth.GeneratePasswordRequest;
import anagrafica.dto.auth.LoginRequest;
import anagrafica.dto.auth.LoginResponse;
import anagrafica.service.auth.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/login")
@Tag(name = "Gestione Autenticazione")
@Slf4j
public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LoginResponse> login(@RequestBody final LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "/generate-password", produces = org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String, String>> generatePwd(@RequestBody GeneratePasswordRequest request) throws IOException {
        final String pwd = authService.encodePassword(request.getPassword());
        final Map<String, String> result = new HashMap<>();
        result.put("password", pwd);
        return ResponseEntity.ok(result);
    }

}
