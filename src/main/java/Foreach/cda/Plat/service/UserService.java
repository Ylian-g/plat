package Foreach.cda.Plat.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Foreach.cda.Plat.entity.Recette;
import Foreach.cda.Plat.entity.User;
import Foreach.cda.Plat.repository.RecetteRepository;
import Foreach.cda.Plat.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final RecetteRepository recetteRepository;
    
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    public User find(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void addFavori(Long userId, Long recetteId) {
        User user = userRepository.findById(userId).orElse(null);
        Recette recette = recetteRepository.findById(recetteId).orElse(null);
        
        if (user != null && recette != null && !user.getRecettesFavorites().contains(recette)) {
            user.getRecettesFavorites().add(recette);
            userRepository.save(user);
        }
    }
    
    @Transactional
    public void removeFavori(Long userId, Long recetteId) {
        User user = userRepository.findById(userId).orElse(null);
        Recette recette = recetteRepository.findById(recetteId).orElse(null);
        
        if (user != null && recette != null) {
            user.getRecettesFavorites().remove(recette);
            userRepository.save(user);
        }
    }
    
    @Transactional
    public List<Recette> getFavoris(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user != null ? user.getRecettesFavorites() : List.of();
    }
}