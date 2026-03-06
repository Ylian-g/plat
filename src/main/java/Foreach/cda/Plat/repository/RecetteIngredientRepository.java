package Foreach.cda.Plat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Foreach.cda.Plat.entity.Recette;
import Foreach.cda.Plat.entity.RecetteIngredient;

@Repository
public interface RecetteIngredientRepository extends JpaRepository<RecetteIngredient, Long> {
    
    List<RecetteIngredient> findByRecette(Recette recette);
    
    Optional<RecetteIngredient> findByRecetteIdAndIngredientId(Long recetteId, Long ingredientId);
    
    void deleteByRecetteIdAndIngredientId(Long recetteId, Long ingredientId);
}