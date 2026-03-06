package Foreach.cda.Plat.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "recettes")
public class Recette {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RE_ID")
    private Long id;

    @Column(name = "RE_NomPlat", nullable = false)
    private String nomPlat;

    @Column(name = "RE_DureePreparation", nullable = false)
    private Integer dureePreparation;

    @Column(name = "RE_DureeCuisson", nullable = false)
    private Integer dureeCuisson;

    @Column(name = "RE_NombreCalorique", nullable = false)
    private Integer nombreCalorique;

    @Column(name = "RE_Partage", nullable = false)
    private Boolean partage = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createur_id", nullable = false)
    private User createur;

    @ManyToMany(mappedBy = "recettesFavorites", targetEntity = User.class, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User> utilisateursFavoris;

    @OneToMany(mappedBy = "recette", targetEntity = RecetteIngredient.class, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<RecetteIngredient> recetteIngredients;
}