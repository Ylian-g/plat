package Foreach.cda.Plat.dto;

public record RecetteIngredientResponseDTO(
    Long id,
    IngredientResponseDTO ingredient,
    Double quantite,
    String unite
) {}