package Foreach.cda.Plat.dto;

public record TokenDTO(
    String token,
    String type,
    UserResponseDTO user
) {
    public TokenDTO(String token, UserResponseDTO user) {
        this(token, "Bearer", user);
    }
}
