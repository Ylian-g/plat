package Foreach.cda.Plat.dto;

import Foreach.cda.Plat.entity.Role;

public record UserResponseDTO(
    Long id,
    String nom,
    String prenom,
    Role role,
    String telephone,
    String mail
) {}