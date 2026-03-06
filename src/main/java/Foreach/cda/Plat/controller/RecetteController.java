package Foreach.cda.Plat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Foreach.cda.Plat.dto.RecetteIngredientRequestDTO;
import Foreach.cda.Plat.dto.RecetteRequestDTO;
import Foreach.cda.Plat.dto.RecetteResponseDTO;
import Foreach.cda.Plat.entity.Recette;
import Foreach.cda.Plat.entity.User;
import Foreach.cda.Plat.mapper.RecetteMapper;
import Foreach.cda.Plat.repository.UserRepository;
import Foreach.cda.Plat.service.RecetteService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/recettes", produces = "application/json")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RecetteController {
    
    private final RecetteService recetteService;
    private final RecetteMapper recetteMapper;
    private final UserRepository userRepository;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RecetteResponseDTO>> getRecettes() {
        return ResponseEntity.ok(recetteMapper.toDTO(recetteService.findAll()));
    }
    
    @GetMapping("/partagees")
    public ResponseEntity<List<RecetteResponseDTO>> getRecettesPartagees() {
        return ResponseEntity.ok(recetteMapper.toDTO(recetteService.findPartagees()));
    }
    
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<RecetteResponseDTO>> getRecettesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(recetteMapper.toDTO(recetteService.findByUser(userId)));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getRecette(@PathVariable Long id) {
        Recette recette = recetteService.find(id);
        
        if (recette == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(recetteMapper.toDTO(recette));
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<RecetteResponseDTO>> searchRecettes(@RequestParam String q) {
        return ResponseEntity.ok(recetteMapper.toDTO(recetteService.search(q)));
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<String> addRecette(@RequestBody RecetteRequestDTO dto) {
        Recette recette = recetteMapper.toEntity(dto);
        
        User createur = userRepository.findById(dto.createurId()).orElse(null);
        if (createur == null) {
            return ResponseEntity.badRequest().body("{\"message\": \"Créateur introuvable\"}");
        }
        
        recette.setCreateur(createur);
        recetteService.save(recette);
        
        return ResponseEntity.ok("{\"message\": \"Recette ajoutée avec succès\"}");
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<String> updateRecette(@PathVariable Long id, @RequestBody RecetteRequestDTO dto) {
        Recette recette = recetteMapper.toEntity(dto);
        recette.setId(id);
        
        User createur = userRepository.findById(dto.createurId()).orElse(null);
        if (createur == null) {
            return ResponseEntity.badRequest().body("{\"message\": \"Créateur introuvable\"}");
        }
        
        recette.setCreateur(createur);
        recetteService.save(recette);
        
        return ResponseEntity.ok("{\"message\": \"Recette modifiée avec succès\"}");
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteRecette(@PathVariable Long id) {
        recetteService.remove(id);
        return ResponseEntity.ok("{\"message\": \"Recette supprimée avec succès\"}");
    }
    
    @PatchMapping("/{id}/partage")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> togglePartage(@PathVariable Long id) {
        Recette recette = recetteService.togglePartage(id);
        
        if (recette == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(recetteMapper.toDTO(recette));
    }
    
    @PostMapping("/{id}/ingredients")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> addIngredient(@PathVariable Long id, @RequestBody RecetteIngredientRequestDTO dto) {
        Recette recette = recetteService.addIngredient(id, dto.ingredientId(), dto.quantite(), dto.unite());
        
        if (recette == null) {
            return ResponseEntity.badRequest().body("{\"message\": \"Recette ou ingrédient introuvable\"}");
        }
        
        return ResponseEntity.ok(recetteMapper.toDTO(recette));
    }
    
    @PutMapping("/{id}/ingredients/{ingredientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> updateIngredient(
        @PathVariable Long id,
        @PathVariable Long ingredientId,
        @RequestBody RecetteIngredientRequestDTO dto) {
        
        Recette recette = recetteService.updateIngredient(id, ingredientId, dto.quantite(), dto.unite());
        
        if (recette == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(recetteMapper.toDTO(recette));
    }
    
    @DeleteMapping("/{id}/ingredients/{ingredientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<String> removeIngredient(@PathVariable Long id, @PathVariable Long ingredientId) {
        recetteService.removeIngredient(id, ingredientId);
        return ResponseEntity.ok("{\"message\": \"Ingrédient retiré avec succès\"}");
    }
}