package Foreach.cda.Plat.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import Foreach.cda.Plat.dto.UserRequestDTO;
import Foreach.cda.Plat.dto.UserResponseDTO;
import Foreach.cda.Plat.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    UserResponseDTO toDTO(User user);
    
    List<UserResponseDTO> toDTO(List<User> users);
    
    User toEntity(UserRequestDTO dto);
}
