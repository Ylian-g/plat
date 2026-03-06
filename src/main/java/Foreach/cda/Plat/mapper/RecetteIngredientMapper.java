package Foreach.cda.Plat.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import Foreach.cda.Plat.dto.RecetteIngredientResponseDTO;
import Foreach.cda.Plat.entity.RecetteIngredient;

@Mapper(componentModel = "spring", uses = {IngredientMapper.class})
public interface RecetteIngredientMapper {
    
    RecetteIngredientResponseDTO toDTO(RecetteIngredient recetteIngredient);
    
    List<RecetteIngredientResponseDTO> toDTO(List<RecetteIngredient> recetteIngredients);
}
