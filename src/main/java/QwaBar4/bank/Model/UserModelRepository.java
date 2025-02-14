package QwaBar4.bank.Model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserModelRepository extends JpaRepository<UserModel, Long>{
    
    Optional<UserModel> findByUsername(String username);
    
}
