package Foreach.cda.Plat.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import Foreach.cda.Plat.entity.Ingredient;
import Foreach.cda.Plat.entity.Recette;
import Foreach.cda.Plat.entity.RecetteIngredient;
import Foreach.cda.Plat.entity.User;
import Foreach.cda.Plat.repository.IngredientRepository;
import Foreach.cda.Plat.repository.RecetteIngredientRepository;
import Foreach.cda.Plat.repository.RecetteRepository;
import Foreach.cda.Plat.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecetteService {
    
    private final RecetteRepository recetteRepository;
    private final UserRepository userRepository;
    private final IngredientRepository ingredientRepository;
    private final RecetteIngredientRepository recetteIngredientRepository;
    
    public List<Recette> findAll() {
        return recetteRepository.findAll();
    }
    
    public Recette find(Long id) {
        return recetteRepository.findById(id).orElse(null);
    }
    
    public List<Recette> findPartagees() {
        return recetteRepository.findByPartageTrue();
    }
    
    public List<Recette> findByUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user != null ? recetteRepository.findByCreateur(user) : List.of();
    }
    
    public List<Recette> search(String query) {
        return recetteRepository.findByNomPlatContainingIgnoreCase(query);
    }
    
    public void save(Recette recette) {
        recetteRepository.save(recette);
    }
    
    public void remove(Long id) {
        recetteRepository.deleteById(id);
    }
    
    public Recette togglePartage(Long id) {
        Recette recette = recetteRepository.findById(id).orElse(null);
        if (recette != null) {
            recette.setPartage(!recette.getPartage());
            recetteRepository.save(recette);
        }
        return recette;
    }
    
    @Transactional
    public Recette addIngredient(Long recetteId, Long ingredientId, Double quantite, String unite) {
        Recette recette = recetteRepository.findById(recetteId).orElse(null);
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElse(null);
        
        if (recette != null && ingredient != null) {
            RecetteIngredient ri = new RecetteIngredient();
            ri.setRecette(recette);
            ri.setIngredient(ingredient);
            ri.setQuantite(quantite);
            ri.setUnite(unite);
            recetteIngredientRepository.save(ri);
            
            recette = recetteRepository.findById(recetteId).orElse(null);
        }
        return recette;
    }
    
    public Recette updateIngredient(Long recetteId, Long ingredientId, Double quantite, String unite) {
        RecetteIngredient ri = recetteIngredientRepository.findByRecetteIdAndIngredientId(recetteId, ingredientId).orElse(null);
        
        if (ri != null) {
            ri.setQuantite(quantite);
            ri.setUnite(unite);
            recetteIngredientRepository.save(ri);
        }
        
        return recetteRepository.findById(recetteId).orElse(null);
    }
    
    public void removeIngredient(Long recetteId, Long ingredientId) {
        recetteIngredientRepository.deleteByRecetteIdAndIngredientId(recetteId, ingredientId);
    }
}