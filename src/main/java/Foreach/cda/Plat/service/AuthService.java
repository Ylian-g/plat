package Foreach.cda.Plat.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import Foreach.cda.Plat.config.JwtUtil;
import Foreach.cda.Plat.dto.LoginDTO;
import Foreach.cda.Plat.dto.TokenDTO;
import Foreach.cda.Plat.dto.UserRequestDTO;
import Foreach.cda.Plat.entity.User;
import Foreach.cda.Plat.mapper.UserMapper;
import Foreach.cda.Plat.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public TokenDTO register(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByMail(userRequestDTO.mail())) {
            return null;
        }

        if (userRepository.existsByTelephone(userRequestDTO.telephone())) {
            return null;
        }

        User user = userMapper.toEntity(userRequestDTO);
        user.setPassword(passwordEncoder.encode(userRequestDTO.password()));

        user = userRepository.save(user);

        String token = jwtUtil.generateToken(user.getMail(), user.getRole().name());

        return new TokenDTO(token, userMapper.toDTO(user));
    }

    public TokenDTO login(LoginDTO loginDTO) {
        User user = userRepository.findByMail(loginDTO.mail()).orElse(null);

        if (user == null || !passwordEncoder.matches(loginDTO.password(), user.getPassword())) {
            return null;
        }

        String token = jwtUtil.generateToken(user.getMail(), user.getRole().name());

        return new TokenDTO(token, userMapper.toDTO(user));
    }
}
