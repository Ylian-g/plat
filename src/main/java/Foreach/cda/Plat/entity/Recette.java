package Foreach.cda.Plat.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recettes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recette {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nomPlat;
    
    @Column(nullable = false)
    private Integer dureePreparation; 
    
    @Column(nullable = false)
    private Integer dureeCuisson;
    
    @Column(nullable = false)
    private Integer nombreCalorique;
    
    @Column(nullable = false)
    private Boolean partage = false;
    
    @ManyToOne
    @JoinColumn(name = "createur_id", nullable = false)
    private User createur;
    
    @ManyToMany(mappedBy = "recettesFavorites")
    private List<User> utilisateursFavoris = new ArrayList<>();
    
    @OneToMany(mappedBy = "recette", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecetteIngredient> recetteIngredients = new ArrayList<>();
}