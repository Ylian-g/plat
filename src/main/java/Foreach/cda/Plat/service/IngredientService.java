package Foreach.cda.Plat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import Foreach.cda.Plat.entity.Ingredient;
import Foreach.cda.Plat.entity.TypeIngredient;
import Foreach.cda.Plat.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngredientService {
    
    private final IngredientRepository ingredientRepository;
    
    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }
    
    public Ingredient find(Long id) {
        return ingredientRepository.findById(id).orElse(null);
    }
    
    public List<Ingredient> findByType(TypeIngredient type) {
        return ingredientRepository.findByType(type);
    }
    
    public List<Ingredient> search(String query) {
        return ingredientRepository.findByLibelleContainingIgnoreCase(query);
    }
    
    public void save(Ingredient ingredient) {
        ingredientRepository.save(ingredient);
    }
    
    public void remove(Long id) {
        ingredientRepository.deleteById(id);
    }
}