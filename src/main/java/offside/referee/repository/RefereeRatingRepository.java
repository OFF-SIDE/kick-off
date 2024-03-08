package offside.referee.repository;

import jakarta.transaction.Transactional;
import offside.referee.domain.RefereeRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface RefereeRatingRepository extends JpaRepository<RefereeRating,Integer> {

}
