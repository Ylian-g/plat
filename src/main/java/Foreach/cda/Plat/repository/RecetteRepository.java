package Foreach.cda.Plat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Foreach.cda.Plat.entity.Recette;
import Foreach.cda.Plat.entity.User;

@Repository
public interface RecetteRepository extends JpaRepository<Recette, Long> {
    
    List<Recette> findByCreateur(User createur);
    
    List<Recette> findByPartageTrue();
    
    List<Recette> findByNomPlatContainingIgnoreCase(String nom);
}
