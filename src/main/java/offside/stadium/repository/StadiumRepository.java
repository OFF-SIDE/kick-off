package offside.stadium.repository;

import offside.stadium.domain.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface StadiumRepository extends JpaRepository<Stadium, Integer> {
    Optional<Stadium> findByXAndYAndCategory(float x, float y, String category);
    @Query("SELECT e FROM Stadium e WHERE e.category = :category AND e.x BETWEEN :startX AND :endX AND e.y BETWEEN :startY AND :endY")
    List<Stadium> findAllBetweenLocationAndByCategory(@Param("category") String category, @Param("startX") float startX, @Param("endX") float endX, @Param("startY") float startY, @Param("endY") float endY);
    
    @Query("SELECT e FROM Stadium e WHERE e.name LIKE %:searchName%")
    List<Stadium> findAllBySearchName(@Param("searchName") String searchName);
}
