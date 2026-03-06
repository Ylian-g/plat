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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "US_ID")
    private Long id;
    
    @Column(name = "US_Nom", nullable = false)
    private String nom;
    
    @Column(name = "US_Prenom", nullable = false)
    private String prenom;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "US_Role", nullable = false)
    private Role role;
    
    @Column(name = "US_Password", nullable = false)
    private String password;
    
    @Column(name = "US_Telephone", nullable = false, unique = true, length = 10)
    private String telephone;
    
    @Column(name = "US_Mail", nullable = false, unique = true)
    private String mail;
    
    @OneToMany(mappedBy = "createur", targetEntity = Recette.class, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Recette> recettesPersonnelles;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_favoris",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "recette_id")
    )
    @JsonIgnore
    private List<Recette> recettesFavorites;
}