package offside.referee.repository;

import jakarta.transaction.Transactional;
import java.util.List;
import offside.CategoryEnum;
import offside.StatusEnum;
import offside.referee.domain.Referee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface RefereeRepository extends JpaRepository<Referee, Integer> {
    List<Referee> findAllByIsHiringAndCategoryAndStatus(Boolean isHiring, CategoryEnum category,StatusEnum status);
}
