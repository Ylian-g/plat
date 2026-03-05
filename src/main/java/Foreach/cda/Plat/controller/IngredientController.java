package Foreach.cda.Plat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Foreach.cda.Plat.dto.IngredientRequestDTO;
import Foreach.cda.Plat.dto.IngredientResponseDTO;
import Foreach.cda.Plat.entity.Ingredient;
import Foreach.cda.Plat.entity.TypeIngredient;
import Foreach.cda.Plat.mapper.IngredientMapper;
import Foreach.cda.Plat.service.IngredientService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/ingredients", produces = "application/json")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class IngredientController {
    
    private final IngredientService ingredientService;
    private final IngredientMapper ingredientMapper;
    
    @GetMapping
    public ResponseEntity<List<IngredientResponseDTO>> getIngredients() {
        return ResponseEntity.ok(ingredientMapper.toDTO(ingredientService.findAll()));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getIngredient(@PathVariable Long id) {
        Ingredient ingredient = ingredientService.find(id);
        
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(ingredientMapper.toDTO(ingredient));
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<IngredientResponseDTO>> getIngredientsByType(@PathVariable TypeIngredient type) {
        return ResponseEntity.ok(ingredientMapper.toDTO(ingredientService.findByType(type)));
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<IngredientResponseDTO>> searchIngredients(@RequestParam String q) {
        return ResponseEntity.ok(ingredientMapper.toDTO(ingredientService.search(q)));
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<String> addIngredient(@RequestBody IngredientRequestDTO dto) {
        Ingredient ingredient = ingredientMapper.toEntity(dto);
        ingredientService.save(ingredient);
        return ResponseEntity.ok("{\"message\": \"Ingrédient ajouté avec succès\"}");
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<String> updateIngredient(@PathVariable Long id, @RequestBody IngredientRequestDTO dto) {
        Ingredient ingredient = ingredientMapper.toEntity(dto);
        ingredient.setId(id);
        ingredientService.save(ingredient);
        return ResponseEntity.ok("{\"message\": \"Ingrédient modifié avec succès\"}");
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteIngredient(@PathVariable Long id) {
        ingredientService.remove(id);
        return ResponseEntity.ok("{\"message\": \"Ingrédient supprimé avec succès\"}");
    }
}