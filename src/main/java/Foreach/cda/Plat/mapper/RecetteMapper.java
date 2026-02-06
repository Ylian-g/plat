package Foreach.cda.Plat.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import Foreach.cda.Plat.dto.RecetteRequestDTO;
import Foreach.cda.Plat.dto.RecetteResponseDTO;
import Foreach.cda.Plat.entity.Recette;

@Mapper(componentModel = "spring", uses = {UserMapper.class, RecetteIngredientMapper.class})
public interface RecetteMapper {
    
    @Mapping(source = "recetteIngredients", target = "ingredients")
    RecetteResponseDTO toDTO(Recette recette);
    
    List<RecetteResponseDTO> toDTO(List<Recette> recettes);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createur", ignore = true)
    @Mapping(target = "utilisateursFavoris", ignore = true)
    @Mapping(target = "recetteIngredients", ignore = true)
    Recette toEntity(RecetteRequestDTO dto);
}