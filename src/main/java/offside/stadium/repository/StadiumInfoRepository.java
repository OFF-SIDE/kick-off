package offside.stadium.repository;

import jakarta.transaction.Transactional;
import offside.stadium.domain.StadiumInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface StadiumInfoRepository extends JpaRepository<StadiumInfo, Integer>  {}
