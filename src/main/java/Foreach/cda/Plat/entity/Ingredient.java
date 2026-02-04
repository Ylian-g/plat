package Foreach.cda.Plat.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "ingredients")
public class Ingredient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IN_ID")
    private Long id;
    
    @Column(name = "IN_Libelle", nullable = false)
    private String libelle;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "IN_Type", nullable = false)
    private TypeIngredient type;
    
    @Column(name = "IN_NombreCalorie", nullable = false)
    private Integer nombreCalorie;
    
    @OneToMany(mappedBy = "ingredient", targetEntity = RecetteIngredient.class, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<RecetteIngredient> recetteIngredients;
}