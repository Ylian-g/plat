package Foreach.cda.Plat.dto;

import java.util.List;

public record RecetteResponseDTO(
    Long id,
    String nomPlat,
    Integer dureePreparation,
    Integer dureeCuisson,
    Integer nombreCalorique,
    Boolean partage,
    UserResponseDTO createur,
    List<RecetteIngredientResponseDTO> ingredients
) {}