package multiple_authentication.multiple_authentication.repo;

import multiple_authentication.multiple_authentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsJpaRepo  extends JpaRepository<User,Integer> {


    Optional<User> findByUsername(String uname);
}
