package offside.referee.repository;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import offside.CategoryEnum;
import offside.StatusEnum;
import offside.referee.domain.Referee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface RefereeRepository extends JpaRepository<Referee, Integer> {
    @Query("SELECT db FROM Referee db WHERE db.isHiring = :isHiring AND db.category = :category AND db.status = :status AND db.startDate <= :endDate AND db.endDate >= :startDate AND db.startTime <= :endTime AND db.endTime > :startTime ORDER BY db.createdAt desc")
    List<Referee> findAllBySearch(Integer startTime, Integer endTime, LocalDateTime startDate, LocalDateTime endDate, Boolean isHiring, CategoryEnum category, StatusEnum status);
    
    List<Referee> findTop3ByIsHiringOrderByCreatedAtDesc(Boolean isHiring);
    
    List<Referee> findAllByIsHiringAndUserId(Boolean isHiring, Integer userId);
    
}
