package multiple_authentication.multiple_authentication.repo;

import multiple_authentication.multiple_authentication.model.UserSecretKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSecretKeyJpaRepo  extends JpaRepository<UserSecretKey,Integer> {

    Optional<UserSecretKey> findByUsername(String uname);
}
