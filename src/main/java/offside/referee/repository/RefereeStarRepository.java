package offside.referee.repository;

import jakarta.transaction.Transactional;
import offside.referee.domain.RefereeStar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface RefereeStarRepository extends JpaRepository<RefereeStar, Integer> {

}
