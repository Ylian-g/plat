package Foreach.cda.Plat.dto;

import Foreach.cda.Plat.entity.TypeIngredient;

public record IngredientRequestDTO(
    String libelle,
    TypeIngredient type,
    Integer nombreCalorie
) {}