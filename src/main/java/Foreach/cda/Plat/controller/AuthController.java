package Foreach.cda.Plat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Foreach.cda.Plat.dto.LoginDTO;
import Foreach.cda.Plat.dto.TokenDTO;
import Foreach.cda.Plat.dto.UserRequestDTO;
import Foreach.cda.Plat.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/auth", produces = "application/json")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDTO userRequestDTO) {
        TokenDTO tokenDTO = authService.register(userRequestDTO);

        if (tokenDTO == null) {
            return ResponseEntity.badRequest().body("{\"message\": \"Email ou telephone deja utilise\"}");
        }

        return new ResponseEntity<>(tokenDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        TokenDTO tokenDTO = authService.login(loginDTO);

        if (tokenDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Email ou mot de passe incorrect\"}");
        }

        return ResponseEntity.ok(tokenDTO);
    }
}
