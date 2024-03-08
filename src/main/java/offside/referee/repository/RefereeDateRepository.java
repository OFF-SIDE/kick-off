package offside.referee.repository;

import jakarta.transaction.Transactional;
import offside.referee.domain.RefereeDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface RefereeDateRepository extends JpaRepository<RefereeDate, Integer> {

}
