package offside.stadium.repository;

import jakarta.transaction.Transactional;
import java.util.Optional;
import offside.stadium.domain.StadiumStar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional

public interface StadiumStarRepository extends JpaRepository<StadiumStar, Integer> {
    Optional<StadiumStar> findByUserIdAndStadiumId(Integer userId, Integer stadiumId);
    void deleteByUserIdAndStadiumId(Integer userId, Integer stadiumId);
}
