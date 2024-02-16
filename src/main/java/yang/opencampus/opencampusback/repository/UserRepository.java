package yang.opencampus.opencampusback.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import yang.opencampus.opencampusback.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
    Optional<User> findByEmail(String email);
    public boolean existsByEmail(String email);
}
