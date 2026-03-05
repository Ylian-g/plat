package Foreach.cda.Plat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Foreach.cda.Plat.dto.RecetteResponseDTO;
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
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.remove(id);
        return ResponseEntity.ok("{\"message\": \"Utilisateur supprimé avec succès\"}");
    }
    
    @PostMapping("/{id}/favoris/{recetteId}")
    public ResponseEntity<String> addFavori(@PathVariable Long id, @PathVariable Long recetteId) {
        userService.addFavori(id, recetteId);
        return ResponseEntity.ok("{\"message\": \"Recette ajoutée aux favoris\"}");
    }
    
    @DeleteMapping("/{id}/favoris/{recetteId}")
    public ResponseEntity<String> removeFavori(@PathVariable Long id, @PathVariable Long recetteId) {
        userService.removeFavori(id, recetteId);
        return ResponseEntity.ok("{\"message\": \"Recette retirée des favoris\"}");
    }
    
    @GetMapping("/{id}/favoris")
    public ResponseEntity<List<RecetteResponseDTO>> getFavoris(@PathVariable Long id) {
        return ResponseEntity.ok(recetteMapper.toDTO(userService.getFavoris(id)));
    }
}