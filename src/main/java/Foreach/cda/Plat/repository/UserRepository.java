package Foreach.cda.Plat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Foreach.cda.Plat.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByMail(String mail);
    
    Optional<User> findByTelephone(String telephone);
    
    boolean existsByMail(String mail);
    
    boolean existsByTelephone(String telephone);
}

