package Foreach.cda.Plat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Foreach.cda.Plat.entity.Ingredient;
import Foreach.cda.Plat.entity.TypeIngredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    
    Optional<Ingredient> findByLibelleIgnoreCase(String libelle);
    
    List<Ingredient> findByType(TypeIngredient type);
    
    List<Ingredient> findByLibelleContainingIgnoreCase(String libelle);
}