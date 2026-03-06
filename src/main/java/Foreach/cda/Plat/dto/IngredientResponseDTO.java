package Foreach.cda.Plat.dto;

import Foreach.cda.Plat.entity.TypeIngredient;

public record IngredientResponseDTO(
    Long id,
    String libelle,
    TypeIngredient type,
    Integer nombreCalorie
) {}