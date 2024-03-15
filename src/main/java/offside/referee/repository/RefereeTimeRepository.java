package offside.referee.repository;

import jakarta.transaction.Transactional;
import java.util.List;
import offside.referee.domain.RefereeTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface RefereeTimeRepository extends JpaRepository<RefereeTime,Integer> {
    List<RefereeTime> findAllByRefereeIdInAndHourIn(List<Integer> refereeIdList, List<Integer> hourList);
}
