package Foreach.cda.Plat.dto;

public record RecetteRequestDTO(
    String nomPlat,
    Integer dureePreparation,
    Integer dureeCuisson,
    Integer nombreCalorique,
    Boolean partage,
    Long createurId
) {}