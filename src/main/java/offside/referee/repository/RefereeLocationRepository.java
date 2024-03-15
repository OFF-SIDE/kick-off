package offside.referee.repository;

import jakarta.transaction.Transactional;
import java.util.List;
import offside.LocationEnum;
import offside.referee.domain.RefereeLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface RefereeLocationRepository extends JpaRepository<RefereeLocation, Integer> {
    List<RefereeLocation> findAllByRefereeIdInAndLocationIn(List<Integer> refereeIdList, List<LocationEnum> locationList);
}
