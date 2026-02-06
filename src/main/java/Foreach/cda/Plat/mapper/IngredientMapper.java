package Foreach.cda.Plat.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import Foreach.cda.Plat.dto.IngredientRequestDTO;
import Foreach.cda.Plat.dto.IngredientResponseDTO;
import Foreach.cda.Plat.entity.Ingredient;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    
    IngredientResponseDTO toDTO(Ingredient ingredient);
    
    List<IngredientResponseDTO> toDTO(List<Ingredient> ingredients);
    
    Ingredient toEntity(IngredientRequestDTO dto);
}