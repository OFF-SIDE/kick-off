package offside.auth.repository;

import jakarta.transaction.Transactional;
import java.util.Optional;
import offside.auth.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account,Integer> {
    Optional<Account> findByOauthId(String oauthId);
}
