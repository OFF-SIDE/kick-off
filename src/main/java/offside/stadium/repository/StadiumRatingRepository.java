package offside.stadium.repository;

import java.util.List;
import offside.stadium.domain.StadiumRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StadiumRatingRepository extends JpaRepository<StadiumRating, Integer> {
    List<StadiumRating> findAllByStadiumId(int stadiumId);
}


