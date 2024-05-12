package offside.Notice;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface NoticeRepository extends JpaRepository<Notice,Integer> {
    List<Notice> findAllByOrderByCreatedAtDesc();
}
