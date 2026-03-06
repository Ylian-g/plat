package Foreach.cda.Plat.dto;

public record RecetteIngredientRequestDTO(
    Long ingredientId,
    Double quantite,
    String unite
) {}