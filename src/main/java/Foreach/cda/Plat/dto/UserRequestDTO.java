package Foreach.cda.Plat.dto;

import Foreach.cda.Plat.entity.Role;

public record UserRequestDTO(
    String nom,
    String prenom,
    Role role,
    String password,
    String telephone,
    String mail
) {}