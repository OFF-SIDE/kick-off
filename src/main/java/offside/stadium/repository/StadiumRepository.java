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
    List<Stadium> findAllByXAndY(float X, float Y);
    List<Stadium> findAllByName(String name);
    @Query("SELECT e FROM Stadium e WHERE e.category = :category AND e.X BETWEEN :startX AND :endX AND e.Y BETWEEN :startY AND :endY")
    List<Stadium> findEntitiesBetweenValues(@Param("category") String category, @Param("startX") float startX, @Param("endX") float endX, @Param("startY") float startY, @Param("endY") float endY);
    Optional<Stadium> findById(Integer id);
}
