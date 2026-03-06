package Foreach.cda.Plat.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import Foreach.cda.Plat.dto.RecetteResponseDTO;
import Foreach.cda.Plat.dto.UserRequestDTO;
import Foreach.cda.Plat.dto.UserResponseDTO;
import Foreach.cda.Plat.entity.User;
import Foreach.cda.Plat.mapper.RecetteMapper;
import Foreach.cda.Plat.mapper.UserMapper;
import Foreach.cda.Plat.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/users", produces = "application/json")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final RecetteMapper recetteMapper;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        return ResponseEntity.ok(userMapper.toDTO(userService.findAll()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        User user = userService.find(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userMapper.toDTO(user));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addUser(@RequestBody UserRequestDTO dto) {
        User user = userMapper.toEntity(dto);
        userService.save(user);
        return ResponseEntity.ok("{\"message\": \"Utilisateur ajoute avec succes\"}");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO dto) {
        User user = userMapper.toEntity(dto);
        user.setId(id);
        userService.save(user);
        return ResponseEntity.ok("{\"message\": \"Utilisateur modifie avec succes\"}");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.remove(id);
        return ResponseEntity.ok("{\"message\": \"Utilisateur supprime avec succes\"}");
    }

    @PostMapping("/{id}/favoris/{recetteId}")
    public ResponseEntity<String> addFavori(@PathVariable Long id, @PathVariable Long recetteId) {
        userService.addFavori(id, recetteId);
        return ResponseEntity.ok("{\"message\": \"Recette ajoutee aux favoris\"}");
    }

    @DeleteMapping("/{id}/favoris/{recetteId}")
    public ResponseEntity<String> removeFavori(@PathVariable Long id, @PathVariable Long recetteId) {
        userService.removeFavori(id, recetteId);
        return ResponseEntity.ok("{\"message\": \"Recette retiree des favoris\"}");
    }

    @GetMapping("/{id}/favoris")
    public ResponseEntity<List<RecetteResponseDTO>> getFavoris(@PathVariable Long id) {
        return ResponseEntity.ok(recetteMapper.toDTO(userService.getFavoris(id)));
    }
}
