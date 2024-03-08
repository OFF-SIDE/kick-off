package offside.referee.repository;

import jakarta.transaction.Transactional;
import offside.referee.domain.RefereeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface RefereeCategoryRepository extends JpaRepository<RefereeCategory, Integer> {

}
