package offside.referee.repository;

import jakarta.transaction.Transactional;
import offside.referee.domain.Referee;
import offside.referee.domain.RefereeStar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface RefereeStarRepository extends JpaRepository<RefereeStar, Integer> {
    Optional<RefereeStar> findByUserIdAndRefereeId(Integer userId, Integer refereeId);
    void deleteByUserIdAndRefereeId(Integer userId, Integer refereeId);
    List<RefereeStar> findAllByUserId(Integer userId);
    
    @Query("SELECT rs, r FROM RefereeStar rs LEFT JOIN Referee r ON rs.refereeId = r.id WHERE r.isHiring =:isHiring and rs.userId = :userId")
    List<Object[]> getAllStaredReferee(@Param("userId") Integer userId,@Param("isHiring") Boolean isHiring);
}
