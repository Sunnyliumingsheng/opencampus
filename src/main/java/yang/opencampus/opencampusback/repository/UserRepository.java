package yang.opencampus.opencampusback.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import yang.opencampus.opencampusback.entity.User;

public interface UserRepository extends JpaRepository<User,Integer>{

    Optional<User> findByEmail(String email);
}
